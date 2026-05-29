package com.aurora.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.aurora.common.Result;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalException {

    @Schema(description = "业务异常")
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleServiceException(BusinessException e) {
        log.error(e.getMessage(), e);
        return Result.error(e.getMessage());
    }


    @Schema(description = "权限不足异常")
    @ExceptionHandler(NotPermissionException.class)
    public Result<Void> handleNotPermissionException(NotPermissionException e) {
        log.error(e.getMessage(), e);
        return Result.error(HttpStatus.FORBIDDEN.value(),e.getMessage());
    }


    @Schema(description = "未登录异常")
    @ExceptionHandler(NotLoginException.class)
    public Result<Void> handleRuntimeException(RuntimeException e) {
        log.error("未登录异常：", e);
        return Result.error(HttpStatus.UNAUTHORIZED.value(),"当前用户未登录或 登录已过期");
    }

    @Schema(description = "系统异常")
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统异常：", e);
        return Result.error("系统错误");
    }
}
