package com.hongchu.proxy.service.impl.bilibili;

import com.hongchu.pojo.proxy.bilibili.BilibiliVideoInfo;
import com.hongchu.proxy.proxyApi.BilibiliUrlConfig;
import com.hongchu.proxy.service.bilibili.BilibiliProxyGetVideoInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * B站视频信息获取
 * @author HongChu
 * @date 2023/10/21
 * @description: B站API代理服务
 */
@Slf4j
@Service
public class BilibiliProxyGetVideoInfoServiceImpl implements BilibiliProxyGetVideoInfoService {
    private final WebClient         generalWebClient; // 通用WebClient
    private final BilibiliUrlConfig burl;             // url配置

    public BilibiliProxyGetVideoInfoServiceImpl(WebClient webClient, BilibiliUrlConfig bilibiliUrlConfig) {
        this.generalWebClient = webClient;
        this.burl = bilibiliUrlConfig;
    }

    /**
     * 使用通用WebClient获取视频信息-阻塞版本
     */
    @Override
    public BilibiliVideoInfo getVideoInfoByBvid(String bvid) {
        log.info("使用通用WebClient获取B站视频信息, bvid: {}", bvid);

        return generalWebClient.get()
                .uri(burl.getVideoInfo() + "?bvid=" + bvid)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    log.error("B站API请求失败, 状态码: {}, bvid: {}", response.statusCode(), bvid);
                    return Mono.error(new RuntimeException("B站API请求失败: " + response.statusCode()));
                })
                .bodyToMono(BilibiliVideoInfo.class)
                .block();
    }

    /**
     * 获取视频信息非阻塞版本-返回Mono
     * @param bvid 视频ID
     */
    @Override
    public Mono<BilibiliVideoInfo> getVideoInfoByBvidAsync(String bvid) {
        log.info("非阻塞方式获取B站视频信息, bvid: {}", bvid);

        return generalWebClient.get()
                .uri(burl.getVideoInfo() + "?bvid=" + bvid) // 构建请求URI
                .retrieve() // 获取响应
                .onStatus(HttpStatusCode::isError, response -> {
                    log.error("B站API请求失败, 状态码: {}, bvid: {}", response.statusCode(), bvid);
                    return Mono.error(new RuntimeException("B站API请求失败: " + response.statusCode()));
                })
                .bodyToMono(BilibiliVideoInfo.class)
                .doOnSuccess(info -> log.info("成功获取B站视频信息, bvid: {}", bvid))
                .doOnError(error -> log.error("获取B站视频信息失败, bvid: {}", bvid, error));
    }
}