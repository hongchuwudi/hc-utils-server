package com.hongchu.proxy.service.impl.bilibili;

import com.hongchu.pojo.proxy.bilibili.BilibiliPlayUrlInfo;
import com.hongchu.pojo.proxy.bilibili.PlayUrlRequest;
import com.hongchu.proxy.proxyApi.BilibiliUrlConfig;
import com.hongchu.proxy.service.bilibili.BilibiliPlayUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * B站视频流地址代理服务
 */
@Slf4j
@Service
public class BilibiliPlayUrlServiceImpl implements BilibiliPlayUrlService {

    private final WebClient webClient;
    private final BilibiliUrlConfig burl;
    public BilibiliPlayUrlServiceImpl(BilibiliUrlConfig bilibiliUrlConfig, WebClient webClient) {
        this.webClient = webClient;
        this.burl = bilibiliUrlConfig;
    }

    /**
     * 构建完整URL
     */
    private String buildFullUrl(Map<String, Object> queryParams) {
        UriComponentsBuilder builder =UriComponentsBuilder.fromUriString(burl.getPlay());
        queryParams.forEach((key, value) -> {
            if (value != null) {
                builder.queryParam(key, value);
            }
        });
        return builder.toUriString();
    }

    /**
     * 获取视频流地址 - 基础版本
     * @param request 请求参数
     * @param cookieHeader Cookie头
     * @ return BilibiliPlayUrlInfo
     */
    @Override
    public BilibiliPlayUrlInfo getPlayUrl(PlayUrlRequest request, String cookieHeader) {
        log.info("代理获取B站视频流地址, bvid: {}, avid: {}, cid: {}, qn:{}, fnval:{}, fourk:{},",
                request.getBvid(), request.getAvid(), request.getCid(), request.getQn(), request.getFnval(), request.getFourk());
        log.info("Cookie: {}", cookieHeader);
        // 构建查询参数
        Map<String, Object> queryParams = buildQueryParams(request);

        // 构建Cookie
        if (cookieHeader != null && !cookieHeader.trim().isEmpty())
            cookieHeader =  "SESSDATA=" + cookieHeader.trim();
        else cookieHeader =  "SESSDATA=";

        // 构建完整URL
        String fullUrl = buildFullUrl(queryParams);
        log.info("完整URL: {}", fullUrl);

        // 使用通用WebClient，指定完整URL
        return webClient.get()
                .uri(fullUrl)
                .header("Referer", "https://www.bilibili.com")
                .header("Cookie", cookieHeader)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    log.error("B站视频流API请求失败, 状态码: {}", response.statusCode());
                    return Mono.error(new RuntimeException("B站视频流API请求失败: " + response.statusCode()));
                })
                .bodyToMono(BilibiliPlayUrlInfo.class)
                .block();
    }

    /**
     * 快速获取视频流地址 - 使用常用参数
     * @param bvid 视频ID
     * @param cid 视频分PID
     * @param sessData 用户身份
     */
    @Override
    public BilibiliPlayUrlInfo getPlayUrlQuick(String bvid, Long cid, String sessData) {
        PlayUrlRequest request = new PlayUrlRequest();
        request.setBvid(bvid);
        request.setCid(cid);
        request.setQn(64);      // 720P
        request.setFnval(4048); // 所有DASH格式
        request.setFnver(0);
        request.setFourk(1);    // 允许4K
        request.setPlatform("pc");

        return getPlayUrl(request, sessData);
    }

    /**
     * 获取最高清晰度视频流
     * @param bvid 视频ID
     * @param cid 视频分PID
     * @param sessData 用户身份
     */
    @Override
    public BilibiliPlayUrlInfo getHighestQualityPlayUrl(String bvid, Long cid, String sessData) {
        PlayUrlRequest request = new PlayUrlRequest();
        request.setBvid(bvid);
        request.setCid(cid);
        request.setQn(127);     // 8K超高清
        request.setFnval(4048); // 所有DASH格式
        request.setFnver(0);
        request.setFourk(1);
        request.setPlatform("pc");

        return getPlayUrl(request, sessData);
    }

    /**
     * 构建查询参数
     * @param request 请求参数
     * @return Map<String, Object> 查询参数
     */
    private Map<String, Object> buildQueryParams(PlayUrlRequest request) {
        Map<String, Object> params = new HashMap<>();
        if (request.getBvid() != null) params.put("bvid", request.getBvid());
        if (request.getAvid() != null) params.put("avid", request.getAvid());
        if (request.getCid() != null) params.put("cid", request.getCid());
        if (request.getPlatform() != null) params.put("platform", request.getPlatform());
        if (request.getQn() != null) params.put("qn", request.getQn());
        if (request.getFnval() != null) params.put("fnval", request.getFnval());
        if (request.getFnver() != null) params.put("fnver", request.getFnver());
        if (request.getFourk() != null) params.put("fourk", request.getFourk());
        if (request.getSession() != null) params.put("session", request.getSession());
        if (request.getOtype() != null) params.put("otype", request.getOtype());
        if (request.getType() != null) params.put("type", request.getType());
        if (request.getHigh_quality() != null) params.put("high_quality", request.getHigh_quality());
        if (request.getTry_look() != null) params.put("try_look", request.getTry_look());
        return params;
    }
}