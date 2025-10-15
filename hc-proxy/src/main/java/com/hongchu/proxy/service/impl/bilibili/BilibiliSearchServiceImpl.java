package com.hongchu.proxy.service.impl.bilibili;

import com.hongchu.pojo.proxy.bilibili.search.BilibiliSearchRequest;
import com.hongchu.pojo.proxy.bilibili.search.BilibiliSearchResponse;
import com.hongchu.proxy.proxyApi.BilibiliUrlConfig;
import com.hongchu.proxy.service.BilibiliSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@Slf4j
public class BilibiliSearchServiceImpl implements BilibiliSearchService {

    private final WebClient webClient;
    private final BilibiliUrlConfig bUrls;

    public BilibiliSearchServiceImpl( WebClient webClient, BilibiliUrlConfig bilibiliConfig) {
        this.webClient = webClient;
        this.bUrls = bilibiliConfig;
    }
    
    /**
     * 综合搜索
     */
    public Mono<BilibiliSearchResponse> searchAll(String keyword, String cookie) {
        return searchAll(BilibiliSearchRequest.of(keyword), cookie);
    }

    @Override
    public Mono<BilibiliSearchResponse> searchAll(BilibiliSearchRequest request, String cookie) {
        // 使用UriComponentsBuilder构建完整URL
        String url = UriComponentsBuilder.fromUriString(bUrls.getSearchUrl())
                .queryParam("keyword", request.getKeyword())
                .queryParam("page", request.getPage())
                .queryParam("pagesize", request.getPageSize())
                .build(false) // false表示完全不编码
                .toUriString();

        return webClient.get()
                .uri(url)
                .header("Cookie", "SESSDATA=" + cookie)
                .retrieve()
                .bodyToMono(BilibiliSearchResponse.class)
                .doOnNext(response -> {
                    log.info("完整响应: {}", response);
                    if (response.isSuccess()) log.info("B站搜索成功 - 关键词: {}, 结果数: {}", request.getKeyword(), response.getData().getNumResults());
                    else log.warn("B站搜索失败 - 关键词: {}, 错误: {}", request.getKeyword(), response.getMessage());
                })
                .onErrorResume(e -> {
                    log.error("B站搜索异常 - 关键词: {}", request.getKeyword(), e);
                    return Mono.just(createErrorResponse(e.getMessage()));
                });
    }
    
    /**
     * 分类搜索
     */
    @Override
    public Mono<BilibiliSearchResponse> searchByType(BilibiliSearchRequest request, String cookie) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(bUrls.getTypeSearchUrl())
                .queryParam("search_type", request.getSearchType())
                .queryParam("keyword", request.getKeyword())
                .queryParam("page", request.getPage());
        
        // 可选参数
        Optional.ofNullable(request.getOrder()).ifPresent(order ->
                uriBuilder.queryParam("order", order));
        Optional.ofNullable(request.getOrderSort()).ifPresent(orderSort -> 
                uriBuilder.queryParam("order_sort", orderSort));
        Optional.ofNullable(request.getUserType()).ifPresent(userType -> 
                uriBuilder.queryParam("user_type", userType));
        Optional.ofNullable(request.getDuration()).ifPresent(duration -> 
                uriBuilder.queryParam("duration", duration));
        Optional.ofNullable(request.getTids()).ifPresent(tids -> 
                uriBuilder.queryParam("tids", tids));
        Optional.ofNullable(request.getCategoryId()).ifPresent(categoryId -> 
                uriBuilder.queryParam("category_id", categoryId));
        
        return webClient.get()
                .uri(uriBuilder.build().toUri())
                .header("Cookie", "SESSDATA=" + cookie)
                .retrieve()
                .bodyToMono(BilibiliSearchResponse.class)
                .doOnNext(response -> {
                    if (response.isSuccess()) {
                        log.info("B站分类搜索成功 - 类型: {}, 关键词: {}, 结果数: {}", 
                                request.getSearchType(), request.getKeyword(), 
                                response.getData().getNumResults());
                    } else {
                        log.warn("B站分类搜索失败 - 类型: {}, 关键词: {}, 错误: {}", 
                                request.getSearchType(), request.getKeyword(), 
                                response.getMessage());
                    }
                })
                .onErrorResume(e -> {
                    log.error("B站分类搜索异常 - 类型: {}, 关键词: {}", 
                            request.getSearchType(), request.getKeyword(), e);
                    return Mono.just(createErrorResponse(e.getMessage()));
                });
    }


    private BilibiliSearchResponse createErrorResponse(String message) {
        BilibiliSearchResponse response = new BilibiliSearchResponse();
        response.setCode(-1);
        response.setMessage(message);
        return response;
    }
}