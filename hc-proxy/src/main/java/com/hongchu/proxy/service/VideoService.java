// VideoService.java
package com.hongchu.proxy.service;


import com.hongchu.common.result.Result;
import com.hongchu.pojo.proxy.video.ApiResponse;
import com.hongchu.pojo.proxy.video.Category;
import com.hongchu.pojo.proxy.video.Source;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * 视频服务接口
 */
public interface VideoService {

    /**
     * 统一搜索接口
     *
     * @param sourceKey 视频源标识
     * @param keyword   搜索关键词
     * @param page      页码
     * @return 搜索响应
     */
    Mono<ApiResponse> unifiedSearch(String sourceKey, String keyword, Integer page);

    /**
     * 统一详情接口
     *
     * @param sourceKey 视频源标识
     * @param wd     视频ID
     * @return 详情响应
     */
    Mono<ApiResponse> unifiedDetail(String sourceKey, String wd);

    /**
     * 获取所有视频源名称
     * @return 视频源名称列表
     */
    Result<List<Source>> getAllVideoSources();
}