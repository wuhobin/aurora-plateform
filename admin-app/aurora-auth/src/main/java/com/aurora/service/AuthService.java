package com.aurora.service;


import com.aurora.dto.LoginDTO;
import com.aurora.dto.user.LoginUserInfo;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;

public interface AuthService {

    /**
     * 用户登录
     */
    LoginUserInfo login(LoginDTO loginDTO);

    /**
     * 获取当前登录用户信息
     */
    LoginUserInfo getLoginUserInfo();


    String wechatLogin(WxMpXmlMessage wxMessage);

    /**
     * 获取微信扫码登录验证码
     * @return
     */
    String getWechatLoginCode();

    /**
     * 判断前台用户是否微信登录成功
     * @param loginCode
     * @return
     */
    LoginUserInfo getWechatIsLogin(String loginCode);
}
