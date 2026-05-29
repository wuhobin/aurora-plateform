package com.aurora.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.aurora.common.Result;
import com.aurora.dto.LoginDTO;
import com.aurora.dto.user.LoginUserInfo;
import com.aurora.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Tag(name = "认证管理", description = "认证相关接口")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "用户登录")
    @PostMapping("/auth/login")
    public Result<LoginUserInfo> login(@RequestBody LoginDTO loginDTO) {
        return Result.success(authService.login(loginDTO));
    }

    @Operation(summary = "用户登出")
    @PostMapping("/auth/logout")
    public Result<Void> logout() {
        StpUtil.logout();
        return Result.success(null);
    }

    @GetMapping("/auth/info")
    public Result<LoginUserInfo> getUserInfo() {
        return Result.success(authService.getLoginUserInfo());
    }

    @Operation(summary = "前台获取微信扫码登录验证码")
    @GetMapping("/auth/wechat/getCode")
    public Result<String> getWechatLoginCode(){
        return Result.success(authService.getWechatLoginCode());
    }

    @Operation(summary = "判断前台用户是否微信登录成功")
    @GetMapping("/auth/wechat/isLogin/{loginCode}")
    public Result<LoginUserInfo> getWechatIsLogin(@PathVariable String loginCode){
        return Result.success(authService.getWechatIsLogin(loginCode));
    }
}
