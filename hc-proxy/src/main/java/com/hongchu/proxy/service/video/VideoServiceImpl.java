package com.hongchu.proxy.service.video;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hongchu.common.result.Result;
import com.hongchu.pojo.proxy.video.ApiResponse;
import com.hongchu.pojo.proxy.video.Source;
import com.hongchu.proxy.proxyApi.VideoUrlConfig;
import com.hongchu.proxy.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final VideoUrlConfig videoUrlConfig;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    /**
     * 统一搜索接口
     * @param sourceKey 视频源标识
     * @param keyword   搜索关键词
     * @param page      页码
     * @return 搜索响应
     */
    @Override
    public Mono<ApiResponse> unifiedSearch(String sourceKey, String keyword, Integer page) {
        return doRequest(sourceKey, "search", keyword, page);
    }

    /**
     * 统一详情接口
     * @param sourceKey 视频源标识
     * @param wd        详情ID
     * @return 详情响应
     */
    @Override
    public Mono<ApiResponse> unifiedDetail(String sourceKey, String wd) {
        return doRequest(sourceKey, "detail", wd, null);
    }

    /**
     * 获取所有视频源列表
     * @return 视频源列表响应
     */
    @Override
    public Result<List<Source>> getAllVideoSources() {
        Map<String, VideoUrlConfig.VideoSource> sourceMap = videoUrlConfig.getSources();

        if (sourceMap == null || sourceMap.isEmpty())
            return Result.success(new ArrayList<>());

        List<Source> sourceList = sourceMap.entrySet().stream()
                .map(entry -> Source.builder()
                        .keyName(entry.getKey())
                        .name(entry.getValue().getName())
                        .build())
                .collect(Collectors.toList());

        return Result.success(sourceList);
    }

    private Mono<ApiResponse> doRequest(String sourceKey, String action, String param, Integer page) {
        VideoUrlConfig.VideoSource source = videoUrlConfig.getVideoSource(sourceKey);
        if (source == null)
            return errorResponse("视频源不存在: " + sourceKey);

        String url = buildUrl(source.getUrl(), action, param, page);
        log.info("{}请求URL: {}", action, url);

        return webClient.get()
                .uri(url)
                // 强制设置请求头，要求返回JSON
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                // 使用 exchangeToMono 完全控制响应处理
                .exchangeToMono(clientResponse -> {
                    // 获取响应状态
                    if (!clientResponse.statusCode().is2xxSuccessful())
                        return clientResponse.createException()
                                .flatMap(error -> errorResponse("请求失败: " + error.getMessage()));

                    // 强制读取响应体为字符串，忽略Content-Type
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(responseBody -> parseResponseBody(responseBody, action));
                })
                .onErrorResume(e -> errorResponse(action + "失败: " + e.getMessage()));
    }

    /**
     * 解析响应体（强制解析为JSON）
     */
    private Mono<ApiResponse> parseResponseBody(String responseBody, String action) {
        return Mono.fromCallable(() -> {
            if (responseBody == null || responseBody.trim().isEmpty()) {
                throw new RuntimeException("响应体为空");
            }

            String trimmed = responseBody.trim();

            // 调试日志
            log.debug("收到响应，长度: {}，前50字符: {}",
                    trimmed.length(),
                    trimmed.length() > 50 ? trimmed.substring(0, 50) + "..." : trimmed);

            try {
                // 移除可能的BOM头
                if (trimmed.startsWith("\uFEFF")) trimmed = trimmed.substring(1);

                // 检查是否是JSON格式
                if (!trimmed.startsWith("{") && !trimmed.startsWith("[")) {
                    log.error("响应不是JSON格式: {}", trimmed.substring(0, Math.min(200, trimmed.length())));
                    throw new RuntimeException("响应不是有效的JSON格式");
                }

                // 强制解析为ApiResponse
                ApiResponse response = objectMapper.readValue(trimmed, ApiResponse.class);

                log.info("{}成功: code={}, msg={}, total={}",
                        action, response.getCode(), response.getMessage(), response.getTotal());

                return response;
            } catch (Exception e) {
                log.error("解析响应失败: {}", e.getMessage());
                log.error("原始响应: {}", trimmed);
                throw new RuntimeException("JSON解析失败: " + e.getMessage());
            }
        });
    }

    /**
     * 构建URL（确保中文正确编码）
     */
    private String buildUrl(String baseUrl, String action, String param, Integer page) {
        // 使用StringBuilder手动构建
        StringBuilder url = new StringBuilder(baseUrl);
        String source = videoUrlConfig.getVideoSource("baofeng").getUrl();
        // 检查基础URL是否已有查询参数
        if(!source.equals(baseUrl)){
            if (!baseUrl.contains("?")) url.append("?ac=").append(action);
            else url.append("&ac=").append(action);
        }else {
            if (!baseUrl.contains("?")) url.append("?ac=");
            else url.append("&ac=");
        }

        if (param != null && !param.trim().isEmpty()) url.append("&wd=").append(param);
        if (page != null) url.append("&pg=").append(page);
        return url.toString();
    }

    /**
     * 创建错误响应
     */
    private Mono<ApiResponse> errorResponse(String msg) {
        log.error(msg);
        return Mono.just(ApiResponse.builder()
                .code(0)
                .message(msg)
                .build());
    }
}