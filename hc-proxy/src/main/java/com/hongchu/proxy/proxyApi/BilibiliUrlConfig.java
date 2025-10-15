package com.hongchu.proxy.proxyApi;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *  bilibili 接口配置
 */
@Component
@ConfigurationProperties(prefix = "bilibili.api")
@Data
public class BilibiliUrlConfig {
    private String referer;       // 请求头
    private String userAgent;     // 请求头
    private String base;          // 基础url
    private String play;          // 播放url
    private String login;         // 登录url
    private String loginStatus;   // 登录状态url
    private String videoInfo;     // 视频信息url
    private String searchUrl;     // 搜索url
    private String typeSearchUrl; // 搜索类型url


}