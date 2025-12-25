package com.hongchu.proxy.proxyApi;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 音乐代理接口配置
 */
@Component
@ConfigurationProperties(prefix = "my-music")
@Data
public class MusicUrlConfig {
    private UrlInfo wangyiMusicUrl; // 网易云音乐
    private UrlInfo kuWoMusicUrl;   // 网易云音乐
    private UrlInfo qqMusicUrl;     // 酷狗音乐
    private UrlInfo qishuiMusicUrl; // 奇水音乐

    @Data
    public static class UrlInfo {
        private String url;         // 请求地址
        private String key;         // 请求参数
        private String name;        // 接口名称
    }
}