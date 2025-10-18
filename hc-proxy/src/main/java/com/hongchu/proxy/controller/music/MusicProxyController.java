package com.hongchu.proxy.controller.music;

import com.hongchu.common.annotation.ApiCallLogAnnotation;
import com.hongchu.common.result.Result;
import com.hongchu.pojo.proxy.music.UnifiedSearchResponse;
import com.hongchu.pojo.proxy.music.UnifiedDetailResponse;
import com.hongchu.proxy.service.MusicProxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 音乐代理接口
 * @author HongChu
 * @date 2022/04/01
 */
@Slf4j
@RestController
@RequestMapping("/proxy/music")
public class MusicProxyController {

    private final MusicProxyService musicProxyService;

    public MusicProxyController(MusicProxyService musicProxyService) {
        this.musicProxyService = musicProxyService;
    }

    /**
     *  统一搜索接口
     * @param songName 歌曲名
     * @param source 平台源
     * @return 搜索结果
     */
    @GetMapping("/search")
    @ApiCallLogAnnotation(
            apiName = "音乐代理-统一搜索",
            description = "统一搜索音乐接口,支持多个平台搜索,默认为QQ音乐",
            logRequest = true,
            logResponse = false,
            logException = true
    )
    public Result<UnifiedSearchResponse> searchMusic(
            @RequestParam String songName,
            @RequestParam(defaultValue = "qq") String source) {
        log.info("搜索音乐: {}, 来源: {}", songName, source);
        try {
            UnifiedSearchResponse response = musicProxyService.searchMusic(songName, source);
            return Result.success(response);
        } catch (Exception e) {
            log.error("搜索音乐失败: {}", e.getMessage(), e);
            return Result.fail(500, "搜索音乐失败: " + e.getMessage());
        }
    }

    /**
     * 统一获取音乐详情
     * @param songName 歌曲名
     * @param source  来源
     * @param br 比特率
     * @param n 获取数量
     * @return 音乐详情
     */
    @GetMapping("/detail")
    @ApiCallLogAnnotation(
            apiName = "音乐代理-统一获取音乐详情",
            description = "统一获取音乐详情接口,支持多个平台获取,默认为QQ音乐",
            logRequest = true,
            logResponse = false,
            logException = true
    )
    public Result<UnifiedDetailResponse> getMusicDetail(
            @RequestParam String songName,
            @RequestParam(defaultValue = "qq") String source,
            @RequestParam(required = false) String br,
            @RequestParam(required = false) Integer n) {
        log.info("获取音乐详情: {}, 来源: {}, 音质: {}, 序号: {}", songName, source, br, n);
        try {
            UnifiedDetailResponse response = musicProxyService.getMusicDetail(songName, source, br, n);
            return Result.success(response);
        } catch (Exception e) {
            log.error("获取音乐详情失败: {}", e.getMessage(), e);
            return Result.fail(500, "获取音乐详情失败: " + e.getMessage());
        }
    }

}