package com.hongchu.proxy.aspect;

import com.hongchu.common.annotation.ApiCallLogAnnotation;
import com.hongchu.pojo.entity.ApiCallLog;
import com.hongchu.proxy.mapper.ApiCallLogMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Aspect
@Component
public class ApiCallLogAspect {

    private final ApiCallLogMapper apiCallLogMapper;

    public ApiCallLogAspect(ApiCallLogMapper apiCallLogMapper) {
        this.apiCallLogMapper = apiCallLogMapper;
    }

    // 自定义注解来保证方法调用时添加日志-无侵入式的调用
    @Around("@annotation(apiCallLogAnnotation)")
    public Object aroundApiCall(ProceedingJoinPoint joinPoint, ApiCallLogAnnotation apiCallLogAnnotation) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature(); // 获取方法签名
        Method method = signature.getMethod(); // 获取方法

        ApiCallLog apiCallLog = new ApiCallLog();           // 创建API调用日志对象
        apiCallLog.setStartTime(LocalDateTime.now());       // 设置开始时间
        apiCallLog.setCreatedAt(LocalDateTime.now());       // 设置创建时间

        // 设置API名称
        String apiName = apiCallLogAnnotation.apiName().isEmpty()
                ? method.getName()
                : apiCallLogAnnotation.apiName();
        apiCallLog.setApiName(apiName);

        // 直接从切点参数获取请求信息
        setRequestInfo(apiCallLog, joinPoint.getArgs(), method);

        // 获取请求参数
        Object result = null;
        try {
            result = joinPoint.proceed();
            apiCallLog.setStatus("success");
            apiCallLog.setResponseCode(200);

            // 记录响应数据
            if (apiCallLogAnnotation.logResponse() && result != null) {
                apiCallLog.setResponseData(result.toString());
            }

        } catch (Exception e) {
            apiCallLog.setStatus("failure");
            apiCallLog.setResponseCode(500);
            apiCallLog.setErrorMessage(e.getMessage());
            throw e;
        } finally {
            apiCallLog.setEndTime(LocalDateTime.now());
            long duration = java.time.Duration.between(apiCallLog.getStartTime(), apiCallLog.getEndTime()).toMillis();
            apiCallLog.setDuration(duration);
            saveLogAsync(apiCallLog);
        }

        // 返回结果
        return result;
    }

    // 设置请求信息
    private void setRequestInfo(ApiCallLog apiCallLog, Object[] methodArgs, Method method) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 获取请求信息 如果存在 则设置
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            apiCallLog.setUrl(request.getRequestURL().toString());
            apiCallLog.setMethod(request.getMethod());
            apiCallLog.setCallerIp(getClientIpAddress(request));

            // 记录URL参数到 request_params
            Map<String, String> urlParams = new HashMap<>();
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = request.getParameter(paramName);
                urlParams.put(paramName, paramValue);
            }
            if (!urlParams.isEmpty()) {
                apiCallLog.setRequestParams(urlParams.toString());
            }
        }

        // 记录方法参数到 request_body（去掉引号）
        if (methodArgs != null && methodArgs.length > 0) {
            Parameter[] parameters = method.getParameters();
            StringBuilder params = new StringBuilder();

            for (int i = 0; i < methodArgs.length; i++) {
                Object arg = methodArgs[i];
                if (arg != null && !(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)) {
                    String paramName = parameters[i].getName();
                    // 去掉toString()的引号
                    String paramValue = arg.toString().replace("\"", "");
                    params.append(paramName)
                            .append("=")
                            .append(paramValue)
                            .append(", ");
                }
            }
            // 去掉最后一个逗号和空格
            if (!params.isEmpty()) {
                String requestBody = params.substring(0, params.length() - 2);
                apiCallLog.setRequestBody(requestBody);
            }
        }
    }

    // 获取客户端IP
    private String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            return ip.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    // 异步保存日志
    private void saveLogAsync(ApiCallLog apiCallLog) {
        CompletableFuture.runAsync(() -> {
            try {
                apiCallLogMapper.insert(apiCallLog);
                log.debug("API调用日志保存成功：{}", apiCallLog.getApiName());
            } catch (Exception e) {
                log.error("保存API调用日志失败：{}", e.getMessage());
            }
        });
    }
}