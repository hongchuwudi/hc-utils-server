package com.hongchu.app.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Set;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalGatewayFilter implements Filter {

    private final Set<String> blacklistedIPs = Set.of(
            "192.168.1.100",
            "10.0.0.5"
    );

    private final Set<String> whitelistedIPs = Set.of(
            "127.0.0.1",
            "192.168.1.1"
    );

    private final Map<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();
    private final Map<String, Long> lastRequestTime = new ConcurrentHashMap<>();

    private static final int MAX_REQUESTS_PER_MINUTE = 100;
    private static final long ONE_MINUTE = 60 * 1000L;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String clientIP = getClientIP(httpRequest);

        // 1. 白名单检查
        if (!whitelistedIPs.isEmpty() && !whitelistedIPs.contains(clientIP)) {
            sendErrorResponse(httpResponse, 403, "IP不在白名单中");
            return;
        }

        // 2. 黑名单检查
        if (blacklistedIPs.contains(clientIP)) {
            sendErrorResponse(httpResponse, 403, "IP被禁止访问");
            return;
        }

        // 3. 限流检查
        if (isRateLimited(clientIP)) {
            sendErrorResponse(httpResponse, 429, "请求过于频繁，请稍后重试");
            return;
        }

        // 4. 安全头
        addSecurityHeaders(httpResponse);

        chain.doFilter(request, response);
    }

    private String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private boolean isRateLimited(String clientIP) {
        long currentTime = System.currentTimeMillis();
        Long lastTime = lastRequestTime.get(clientIP);

        // 如果是1分钟内的第一次请求，重置计数器
        if (lastTime == null || currentTime - lastTime > ONE_MINUTE) {
            requestCounts.put(clientIP, new AtomicInteger(1));
            lastRequestTime.put(clientIP, currentTime);
            return false;
        }

        // 增加计数并检查是否超限
        AtomicInteger count = requestCounts.get(clientIP);
        if (count != null && count.incrementAndGet() > MAX_REQUESTS_PER_MINUTE) {
            return true;
        }

        return false;
    }

    private void addSecurityHeaders(HttpServletResponse response) {
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("X-XSS-Protection", "1; mode=block");
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message)
            throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":" + status + ",\"message\":\"" + message + "\"}");
    }
}