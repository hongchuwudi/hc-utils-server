package com.hongchu.proxy.controller.bilibili;

import com.hongchu.pojo.proxy.bilibili.search.BilibiliSearchRequest;
import com.hongchu.proxy.service.BilibiliSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.hongchu.pojo.proxy.bilibili.search.BilibiliSearchResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bilibili")
@Slf4j
public class BilibiliSearchController {
    
    private final BilibiliSearchService searchService;
    
    public BilibiliSearchController(BilibiliSearchService searchService) {
        this.searchService = searchService;
    }
    
    /**
     * 综合搜索
     */
    @GetMapping("/search/all")
    public Mono<BilibiliSearchResponse> searchAll(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestHeader(value = "Cookie", required = false) String cookie) {
        
        log.info("B站综合搜索 - 关键词: {}, 页码: {}, 页大小: {}", keyword, page, pageSize);
        
        BilibiliSearchRequest request = BilibiliSearchRequest.builder()
                .keyword(keyword)
                .page(page)
                .pageSize(pageSize)
                .build();

        log.info("B站综合搜索 - 请求参数: {}", request);
        return searchService.searchAll(request, cookie);
    }
    
    /**
     * 视频搜索
     */
    @GetMapping("/search/video")
    public Mono<BilibiliSearchResponse> searchVideo(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "totalrank") String order,
            @RequestParam(required = false) Integer duration,
            @RequestParam(required = false) Integer tids,
            @RequestHeader(value = "Cookie", required = false) String cookie) {
        
        log.info("B站视频搜索 - 关键词: {}, 页码: {}, 排序: {}", keyword, page, order);
        
        BilibiliSearchRequest request = BilibiliSearchRequest.builder()
                .keyword(keyword)
                .searchType("video")
                .page(page)
                .order(order)
                .duration(duration)
                .tids(tids)
                .build();
        
        return searchService.searchByType(request, cookie);
    }
    
    /**
     * 用户搜索
     */
    @GetMapping("/search/user")
    public Mono<BilibiliSearchResponse> searchUser(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "0") String order,
            @RequestParam(defaultValue = "0") Integer orderSort,
            @RequestParam(required = false) Integer userType,
            @RequestHeader(value = "Cookie", required = false) String cookie) {
        
        log.info("B站用户搜索 - 关键词: {}, 页码: {}", keyword, page);
        
        BilibiliSearchRequest request = BilibiliSearchRequest.builder()
                .keyword(keyword)
                .searchType("bili_user")
                .page(page)
                .order(order)
                .orderSort(orderSort)
                .userType(userType)
                .build();
        
        return searchService.searchByType(request, cookie);
    }
}