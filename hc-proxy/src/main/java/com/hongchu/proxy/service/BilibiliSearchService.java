package com.hongchu.proxy.service;

import com.hongchu.pojo.proxy.bilibili.search.BilibiliSearchRequest;
import com.hongchu.pojo.proxy.bilibili.search.BilibiliSearchResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface BilibiliSearchService {

    /**
     * 综合搜索
     * @param request 综合搜索请求
     * @param cookie Cookie sessdata头
     * @return 搜索结果
     */
    Mono<BilibiliSearchResponse> searchAll(BilibiliSearchRequest request, String cookie);

    /**
     * 分类搜索
     * @param request 分类搜索请求
     * @param cookie Cookie sessdata头
     * @return 搜索结果
     */
    Mono<BilibiliSearchResponse> searchByType(BilibiliSearchRequest request, String cookie);
}
