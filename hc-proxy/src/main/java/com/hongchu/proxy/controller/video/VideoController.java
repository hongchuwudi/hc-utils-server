package com.hongchu.proxy.controller.video;

import com.hongchu.common.result.Result;
import com.hongchu.pojo.proxy.video.ApiResponse;
import com.hongchu.pojo.proxy.video.Category;
import com.hongchu.pojo.proxy.video.Source;
import com.hongchu.proxy.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

// VideoController.java
@Slf4j
@RestController
@RequestMapping("/api/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService; // 注入接口

    @GetMapping("/search")
    public Mono<ApiResponse> search(
            @RequestParam String source,
            @RequestParam String wd,
            @RequestParam(defaultValue = "1") Integer pg) {
        return videoService.unifiedSearch(source, wd, pg);
    }

    @GetMapping("/detail")
    public Mono<ApiResponse> detail(
            @RequestParam String source,
            @RequestParam String wd) {
        return videoService.unifiedDetail(source, wd);
    }


    @GetMapping("/sources")
    public Result<List<Source>> sources() {
        return videoService.getAllVideoSources();
    }
}