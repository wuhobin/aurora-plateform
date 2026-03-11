package com.aurora.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.aurora.common.Constants;
import com.aurora.common.RedisConstants;
import com.aurora.common.ResultCode;
import com.aurora.dto.LoginDTO;
import com.aurora.dto.user.LoginUserInfo;
import com.aurora.entity.SysRole;
import com.aurora.entity.SysUser;
import com.aurora.enums.LoginTypeEnum;
import com.aurora.enums.MenuTypeEnum;
import com.aurora.exception.BusinessException;
import com.aurora.mapper.SysMenuMapper;
import com.aurora.mapper.SysRoleMapper;
import com.aurora.mapper.SysUserMapper;
import com.aurora.service.AuthService;
import com.aurora.utils.BeanCopyUtil;
import com.aurora.utils.IpUtils;
import com.aurora.utils.RedisUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final SysUserMapper userMapper;

    private final SysRoleMapper roleMapper;

    private final SysMenuMapper menuMapper;

    private final RedisUtils redisUtil;

    private final String[] avatarList = {
            "https://api.dicebear.com/6.x/pixel-art/svg?seed=Raccoon",
            "https://api.dicebear.com/6.x/pixel-art/svg?seed=Kitty",
            "https://api.dicebear.com/6.x/pixel-art/svg?seed=Puppy",
            "https://api.dicebear.com/6.x/pixel-art/svg?seed=Bunny",
            "https://api.dicebear.com/6.x/pixel-art/svg?seed=Fox"
    };

    @Override
    public LoginUserInfo login(LoginDTO loginDTO) {
        // 查询用户
        SysUser user = userMapper.selectByUsername(loginDTO.getUsername());

        //校验是否能够登录
        validateLogin(loginDTO, user);

        // 执行登录
        StpUtil.login(user.getId());
        String tokenValue = StpUtil.getTokenValue();

        // 返回用户信息
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        BeanUtils.copyProperties(user, loginUserInfo);
        loginUserInfo.setToken(tokenValue);

        StpUtil.getSession().set(Constants.CURRENT_USER, loginUserInfo);
        return loginUserInfo;
    }

    @Override
    public LoginUserInfo getLoginUserInfo() {
        // 获取当前登录用户ID
        Integer userId = StpUtil.getLoginIdAsInt();
        SysUser user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.ERROR_USER_NOT_EXIST.desc);
        }

        //获取菜单权限列表
        List<String> permissions;
        List<String> roles = roleMapper.selectRolesCodeByUserId(userId);
        if (roles.contains(Constants.ADMIN)) {
            permissions = menuMapper.getPermissionList(MenuTypeEnum.BUTTON.getCode());
        } else {
            permissions = menuMapper.getPermissionListByUserId(userId, MenuTypeEnum.BUTTON.getCode());
        }

        LoginUserInfo loginUserInfo = new LoginUserInfo();
        BeanUtils.copyProperties(user, loginUserInfo);

        loginUserInfo.setRoles(roles);
        loginUserInfo.setPermissions(permissions);
        return loginUserInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String wechatLogin(WxMpXmlMessage wxMessage) {
        String code = wxMessage.getContent();

        //先判断登录码是否已过期
        Object e = redisUtil.get(RedisConstants.WX_LOGIN_USER_CODE + code);
        if (e == null) {
            return "验证码错误或者过期，请重新输入";
        }
        //判断是否已经授权成功
        LoginUserInfo loginUserInfo = wechatLogin(wxMessage.getFromUser());
        //修改redis缓存 以便监听是否已经授权成功
        redisUtil.set(RedisConstants.WX_LOGIN_USER + code, JSONUtil.toJsonStr(loginUserInfo), RedisConstants.MINUTE_EXPIRE, TimeUnit.SECONDS);
        return "网站登录成功！(若页面长时间未跳转请刷新验证码)";
    }

    @Override
    public String getWechatLoginCode() {
        //随机获取4位数字
        String code = "AU" + (int) ((Math.random() * 9 + 1) * 1000);
        redisUtil.set(RedisConstants.WX_LOGIN_USER_CODE + code, code, RedisConstants.FIVE_MINUTES_EXPIRE, TimeUnit.SECONDS);
        return code;
    }

    @Override
    public LoginUserInfo getWechatIsLogin(String loginCode) {
        Object value = redisUtil.get(RedisConstants.WX_LOGIN_USER + loginCode);

        if (value == null) {
            throw new BusinessException("登录失败");
        }

        LoginUserInfo loginUserInfo = JSONUtil.toBean(JSONUtil.parseObj(value), LoginUserInfo.class);

        StpUtil.login(loginUserInfo.getId());
        loginUserInfo.setToken(StpUtil.getTokenValue());

        return loginUserInfo;
    }


    private LoginUserInfo wechatLogin(String openId) {

        SysUser user = userMapper.selectByUsername(openId);
        if (ObjectUtils.isEmpty(user)) {
            String ip = IpUtils.getIp();
            String ipSource = IpUtils.getIp2region(ip);

            // 保存账号信息
            user = SysUser.builder()
                    .username(openId)
                    .password(BCrypt.hashpw(openId))
                    .nickname("WECHAT-" + RandomStringUtils.randomAlphabetic(6))
                    .avatar(avatarList[(int) (Math.random() * avatarList.length)])
                    .loginType(LoginTypeEnum.WECHAT.getType())
                    .lastLoginTime(LocalDateTime.now())
                    .ip(ip)
                    .ipLocation(ipSource)
                    .status(Constants.YES)
                    .build();
            userMapper.insert(user);

            //添加用户角色信息
            this.insertRole(user);
        }

        return BeanCopyUtil.copyObj(user, LoginUserInfo.class);
    }

    /**
     * 添加用户角色信息
     * @param user
     */
    private void insertRole(SysUser user) {
        SysRole sysRole = roleMapper.selectOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getCode, Constants.USER));
        roleMapper.addRoleUser(user.getId(), Collections.singletonList(sysRole.getId()));
    }


    private static void validateLogin(LoginDTO loginDTO, SysUser user) {
        if (user == null) {
            throw new BusinessException(ResultCode.ERROR_USER_NOT_EXIST.desc);
        }

        // 验证密码
        if (!BCrypt.checkpw(loginDTO.getPassword(), user.getPassword())) {
            throw new BusinessException(ResultCode.ERROR_PASSWORD.desc);
        }

        // 验证状态
        if (user.getStatus() != 1) {
            throw new BusinessException(ResultCode.DISABLE_ACCOUNT.desc);
        }
    }

}
