package com.hongchu.proxy.service.impl;

import com.hongchu.pojo.proxy.wallpaper.JinShanResponse;
import com.hongchu.pojo.proxy.wallpaper.JinShanWallpaper;
import com.hongchu.proxy.proxyApi.WallpaperUrlConfig;
import com.hongchu.proxy.service.WallpaperProxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class WallpaperProxyServiceImpl implements WallpaperProxyService {
    private final WebClient          webClient;  // 通用WebClient
    private final WallpaperUrlConfig wUrl;       // url配置


    public WallpaperProxyServiceImpl(WebClient webClient, WallpaperUrlConfig wUrl) {
        this.webClient = webClient;
        this.wUrl = wUrl;
    }

    /**
     * 获取金山毒霸壁纸
     * @param msg    关键词
     * @param type  视频尺寸类型(pc/安卓)
     * @param page  页码
     * @param limit 每页数量
     * @return 金山毒霸壁纸列表
     */
    @Override
    public List<JinShanWallpaper> getJinShanWallpaper(String msg, String type, Integer page, Integer limit) {
        // 使用 UriComponentsBuilder 安全构建URL
        String url = UriComponentsBuilder.fromUriString(wUrl.getJinShanUrl())
                .queryParam("msg", msg)  // 保持原始中文
                .queryParam("type", type)
                .queryParam("page", page)
                .queryParam("limit", limit)
                .build(false)            // false表示完全不编码
                .toUriString();

        log.info("请求金山毒霸壁纸请求完整链接:{}",url);

        // 先查看原始响应
        String rawResponse = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // 然后解析为对象
        JinShanResponse response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(JinShanResponse.class)
                .block();

        return response != null ? response.getData() : Collections.emptyList();
    }
}
