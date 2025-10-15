package com.hongchu.proxy.service.bilibili;

import com.hongchu.pojo.proxy.bilibili.BilibiliVideoInfo;
import org.springframework.http.HttpStatusCode;
import reactor.core.publisher.Mono;

public interface BilibiliProxyGetVideoInfoService {

    /**
     * 使用通用WebClient获取视频信息-阻塞版本
     */
    public BilibiliVideoInfo getVideoInfoByBvid(String bvid);

    /**
     * 获取视频信息非阻塞版本-返回Mono
     * @param bvid 视频ID
     */
    public Mono<BilibiliVideoInfo> getVideoInfoByBvidAsync(String bvid);
}
