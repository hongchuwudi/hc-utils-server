package com.hongchu.proxy.service;

import com.hongchu.pojo.proxy.music.UnifiedDetailResponse;
import com.hongchu.pojo.proxy.music.UnifiedSearchResponse;

public interface MusicProxyService {

    /**
     * 统一搜索入口
     * @param songName 歌曲名
     * @param source 源
     */
    UnifiedSearchResponse searchMusic(String songName, String source);

    /**
     * 统一获取详情入口
     * @param songName 歌曲名
     * @param source 源
     * @param br 音乐音质
     * @param n 音乐搜索序号
     */
    UnifiedDetailResponse getMusicDetail(String songName, String source, String br, Integer n);
}