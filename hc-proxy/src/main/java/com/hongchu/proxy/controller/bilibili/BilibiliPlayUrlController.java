package com.hongchu.proxy.controller.bilibili;

import com.hongchu.common.annotation.ApiCallLogAnnotation;
import com.hongchu.common.result.Result;
import com.hongchu.pojo.proxy.bilibili.BilibiliPlayUrlInfo;
import com.hongchu.pojo.proxy.bilibili.PlayUrlRequest;
import com.hongchu.proxy.service.impl.bilibili.BilibiliPlayUrlServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * B站视频流获取
 * @author HongChu
 * @date 2023/10/21
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/proxy/bilibili")
public class BilibiliPlayUrlController {

    private final BilibiliPlayUrlServiceImpl bilibiliPlayUrlService;

    public BilibiliPlayUrlController(BilibiliPlayUrlServiceImpl bilibiliPlayUrlService) {
        this.bilibiliPlayUrlService = bilibiliPlayUrlService;
    }

    /**
     * 获取视频流地址-使用POJO接收参数
     * @param request 请求参数
     * @param sessData 用户身份
     */
    @GetMapping("/playurl")
    @ApiCallLogAnnotation(
            apiName = "哔哩哔哩-获取视频流地址",
            description = "哔哩哔哩-获取视频流地址",
            logRequest = true,
            logResponse = false,
            logException = true
    )
    public Result<BilibiliPlayUrlInfo.PlayUrlData> getPlayUrlProxy(
            @Valid PlayUrlRequest request,
            @RequestHeader("SESSDATA") String sessData) {
        log.info("代理获取B站视频流地址, bvid: {}, avid: {}, cid: {}, qn:{}, fnval:{}, fourk:{},fnver:{}",
                request.getBvid(), request.getAvid(), request.getCid(),
                request.getQn(), request.getFnval(), request.getFourk(),request.getFnver());
        try {
            // 获取视频流地址
            BilibiliPlayUrlInfo playUrlInfo = bilibiliPlayUrlService.getPlayUrl(request, sessData);

            // 返回结果
            return Result.success(playUrlInfo.getData());
        } catch (Exception e) {
            log.error("代理获取视频流地址失败", e);
            return Result.fail(500, "获取视频流地址失败: " + e.getMessage());
        }
    }

    /**
     * 获取视频流地址 - 快速版本
     * @param bvid 视频ID
     * @param cid 视频分PID
     * @param sessData 用户身份
     */
    @GetMapping("/playurl/quick/{bvid}/{cid}")
    @ApiCallLogAnnotation(
            apiName = "哔哩哔哩-获取视频流地址-快速版本",
            description = "哔哩哔哩-获取视频流地址-快速版本",
            logRequest = true,
            logResponse = false,
            logException = true
    )
    public Result<BilibiliPlayUrlInfo> getPlayUrlQuick(
            @PathVariable String bvid,
            @PathVariable Long cid,
            @RequestHeader("SESSDATA") String sessData) {
        log.info("快速获取视频流地址, bvid: {}, cid: {}", bvid, cid);
        // 获取视频流地址
        BilibiliPlayUrlInfo playUrlInfo = bilibiliPlayUrlService.getPlayUrlQuick(bvid, cid, sessData);
        return Result.success(playUrlInfo);
    }

    /**
     * 获取最高清晰度视频流
     * @param bvid 视频ID
     * @param cid 视频分PID
     * @param sessData 用户cookie
     */
    @GetMapping("/playurl/highest/{bvid}/{cid}")
    public Result<BilibiliPlayUrlInfo> getHighestQualityPlayUrl(
            @PathVariable String bvid,
            @PathVariable Long cid,
            @RequestHeader("SESSDATA") String sessData) {
        log.info("获取最高清晰度视频流, bvid: {}, cid: {}", bvid, cid);

        BilibiliPlayUrlInfo playUrlInfo = bilibiliPlayUrlService.getHighestQualityPlayUrl(bvid, cid, sessData);
        return Result.success(playUrlInfo);
    }
}