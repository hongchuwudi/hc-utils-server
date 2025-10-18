package com.hongchu.proxy.controller.bilibili;

import com.hongchu.common.annotation.ApiCallLogAnnotation;
import com.hongchu.common.result.Result;
import com.hongchu.pojo.proxy.bilibili.BilibiliVideoInfo;
import com.hongchu.proxy.service.impl.bilibili.BilibiliProxyGetVideoInfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * B站视频获取(根据Bvid)
 * @author HongChu
 * @date 2023/10/21
 */
@Slf4j
@RestController
@RequestMapping("/proxy/bilibili")
public class BilibiliProxyGetVideoInfoController {

    private final BilibiliProxyGetVideoInfoServiceImpl bilibiliProxyService;

    public BilibiliProxyGetVideoInfoController(BilibiliProxyGetVideoInfoServiceImpl bilibiliProxyService) {
        this.bilibiliProxyService = bilibiliProxyService;
    }

    /**
     * 获取视频信息-同步接口
     * @param bvid 视频ID
     */
    @GetMapping("/video/{bvid}")
    @ApiCallLogAnnotation(
            apiName = "获取视频信息-同步接口",
            description = "获取视频信息-同步接口",
            logRequest = true,
            logResponse = false,
            logException = true
    )
    public Result<BilibiliVideoInfo> getVideoInfo(@PathVariable String bvid) {
        log.info("代理请求B站视频信息, bvid: {}", bvid);
        try {
            BilibiliVideoInfo videoInfo = bilibiliProxyService.getVideoInfoByBvid(bvid);
            return Result.success(videoInfo);
        } catch (Exception e) {
            log.error("代理获取B站视频信息失败, bvid: {}", bvid, e);
            return Result.fail(500, "获取视频信息失败: " + e.getMessage());
        }
    }

    /**
     * 获取视频信息-非阻塞接口
     * 返回 Mono<Result> 让 Spring WebFlux 处理响应式流
     * @param bvid 视频ID
     */
    @GetMapping("/video/async/{bvid}")
    @ApiCallLogAnnotation(
            apiName = "获取视频信息-非阻塞接口",
            description = "获取视频信息-非阻塞接口",
            logRequest = true,
            logResponse = false,
            logException = true
    )
    public Mono<Result<BilibiliVideoInfo>> getVideoInfoAsync(@PathVariable String bvid) {
        log.info("非阻塞方式代理请求B站视频信息, bvid: {}", bvid);

        return bilibiliProxyService.getVideoInfoByBvidAsync(bvid)
                .map(Result::success)
                .onErrorResume(error -> {
                    log.error("非阻塞方式获取B站视频信息失败, bvid: {}", bvid, error);
                    return Mono.just(Result.fail(500, "获取视频信息失败: " + error.getMessage()));
                });
    }

    /**
     * 获取视频信息-直接返回Mono
     * 适合前端能够处理响应式流的场景
     * @param bvid 视频ID
     */
    @GetMapping("/video/reactive/{bvid}")
    @ApiCallLogAnnotation(
            apiName = "获取视频信息-直接返回Mono",
            description = "获取视频信息-直接返回Mono",
            logRequest = true,
            logResponse = false,
            logException = true
    )
    public Mono<BilibiliVideoInfo> getVideoInfoReactive(@PathVariable String bvid) {
        log.info("响应式获取B站视频信息, bvid: {}", bvid);
        return bilibiliProxyService.getVideoInfoByBvidAsync(bvid);
    }
}