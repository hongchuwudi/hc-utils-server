package com.hongchu.proxy.controller.bilibili;

import com.hongchu.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * B站URL转换
 * 不代理视频流量，只做URL转换和签名
 */
@Slf4j
@RestController
@RequestMapping("/proxy/bilibili")
public class BilibiliUrlConvertController {

    private final String SECRET_KEY = "your-secret-key"; // 密钥

    /**
     * 获取签名后的视频URL
     */
    @GetMapping("/signed-url")
    public Result<String> getSignedUrl(@RequestParam String originalUrl) {
        log.info("URL转换请求: {}", originalUrl);
        
        try {
            // 验证URL格式
            if (!isValidBilibiliUrl(originalUrl)) {
                return Result.fail(400, "非法的B站URL");
            }
            
            // 生成签名URL
            String signedUrl = generateSignedUrl(originalUrl);
            
            log.info("URL转换成功: {} -> {}", originalUrl, signedUrl);
            return Result.success(signedUrl);
            
        } catch (Exception e) {
            log.error("URL转换失败", e);
            return Result.fail(500, "URL转换失败: " + e.getMessage());
        }
    }

    /**
     * 生成签名URL
     */
    private String generateSignedUrl(String originalUrl) {
        // 方法1: 添加时间戳和签名
        long timestamp = System.currentTimeMillis();
        String signature = generateSignature(originalUrl, timestamp);
        
        // 构建签名URL
        String separator = originalUrl.contains("?") ? "&" : "?";
        return originalUrl + separator + 
               "timestamp=" + timestamp + 
               "&signature=" + signature +
               "&app_id=hc-utils"; // 添加应用标识
    }

    /**
     * 生成签名
     */
    private String generateSignature(String url, long timestamp) {
        try {
            String data = url + timestamp + SECRET_KEY;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("签名生成失败", e);
        }
    }

    /**
     * 验证URL合法性
     */
    private boolean isValidBilibiliUrl(String url) {
        return url != null && 
               (url.contains("bilivideo.com") || 
                url.contains("bilibili.com") ||
                url.contains("akamaized.net"));
    }

    /**
     * 验证签名（可选，用于后续扩展）
     */
    private boolean validateSignature(String url, long timestamp, String signature) {
        String expectedSignature = generateSignature(url, timestamp);
        return expectedSignature.equals(signature);
    }
}