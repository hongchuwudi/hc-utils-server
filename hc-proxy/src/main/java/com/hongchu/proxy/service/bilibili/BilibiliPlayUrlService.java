package com.hongchu.proxy.service.bilibili;

import com.hongchu.pojo.proxy.bilibili.BilibiliPlayUrlInfo;
import com.hongchu.pojo.proxy.bilibili.PlayUrlRequest;

public interface BilibiliPlayUrlService {
    /**
     * 获取视频流地址 - 基础版本
     * @param request 请求参数
     * @param cookieHeader Cookie头
     * @ return BilibiliPlayUrlInfo
     */
    public BilibiliPlayUrlInfo getPlayUrl(PlayUrlRequest request, String cookieHeader);
    /**
     * 快速获取视频流地址 - 使用常用参数
     * @param bvid 视频ID
     * @param cid 视频分PID
     * @param sessData 用户身份
     */
    public BilibiliPlayUrlInfo getPlayUrlQuick(String bvid, Long cid, String sessData);

    /**
     * 获取最高清晰度视频流
     * @param bvid 视频ID
     * @param cid 视频分PID
     * @param sessData 用户身份
     */
    public BilibiliPlayUrlInfo getHighestQualityPlayUrl(String bvid, Long cid, String sessData);
}
