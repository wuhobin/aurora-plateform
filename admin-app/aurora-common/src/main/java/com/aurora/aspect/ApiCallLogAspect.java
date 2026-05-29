package com.aurora.aspect;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.aurora.utils.IpUtils;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class ApiCallLogAspect {

    private static final Logger logger = LoggerFactory.getLogger("API_CALL_LOG");

    @Around("execution(* com.aurora.controller..*.*(..))")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = getRequest();
        if (request == null || !request.getRequestURI().startsWith("/api/")) {
            return joinPoint.proceed();
        }

        long startTime = System.currentTimeMillis();
        String userId = "anonymous";
        try {
            if (StpUtil.isLogin()) {
                userId = String.valueOf(StpUtil.getLoginId());
            }
        } catch (Exception ignored) {
        }

        Object result = joinPoint.proceed();

        long spendTime = System.currentTimeMillis() - startTime;
        try {
            logApiCall(request, joinPoint, userId, spendTime);
        } catch (Throwable e) {
            logger.error("API调用日志记录异常", e);
        }

        return result;
    }

    private void logApiCall(HttpServletRequest request, ProceedingJoinPoint joinPoint,
                            String userId, long spendTime) {
        Map<String, Object> logMap = new HashMap<>();
        logMap.put("requestUrl", request.getRequestURI());
        logMap.put("httpMethod", request.getMethod());
        logMap.put("classPath", joinPoint.getTarget().getClass().getName());
        logMap.put("methodName", joinPoint.getSignature().getName());
        logMap.put("userId", userId);
        logMap.put("ip", IpUtils.getIp());
        logMap.put("params", getParams(joinPoint));
        logMap.put("spendTime", spendTime);

        logger.info(JSONUtil.toJsonStr(logMap));
    }

    private Map<String, Object> getParams(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] paramNames = signature.getParameterNames();
        Map<String, Object> paramMap = new HashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            if (isUnloggableArg(args[i])) {
                continue;
            }
            paramMap.put(paramNames[i], args[i]);
        }
        return paramMap;
    }

    private boolean isUnloggableArg(Object arg) {
        return arg instanceof HttpServletRequest
                || arg instanceof ServletRequest
                || arg instanceof ServletResponse
                || arg instanceof MultipartFile
                || arg instanceof InputStream
                || arg instanceof OutputStream;
    }

    private HttpServletRequest getRequest() {
        try {
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            return attrs != null ? attrs.getRequest() : null;
        } catch (Exception e) {
            return null;
        }
    }
}
