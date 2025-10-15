package com.hongchu.proxy.service.impl;

import com.hongchu.pojo.proxy.music.UnifiedMusicItem;
import com.hongchu.pojo.proxy.music.UnifiedMusicSearchItem;
import com.hongchu.pojo.proxy.music.UnifiedSearchResponse;
import com.hongchu.pojo.proxy.music.UnifiedDetailResponse;
import com.hongchu.proxy.proxyApi.MusicUrlConfig;
import com.hongchu.proxy.service.MusicProxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.hongchu.common.util.MapSafeUtils.safeGetInteger;
import static com.hongchu.common.util.MapSafeUtils.safeGetString;

@Slf4j
@Service
public class MusicProxyServiceImpl implements MusicProxyService {

    private final WebClient webClient;
    private final MusicUrlConfig musicUrlConfig;

    @Autowired
    public MusicProxyServiceImpl(WebClient.Builder webClientBuilder, MusicUrlConfig musicUrlConfig) {
        this.webClient = webClientBuilder.build();
        this.musicUrlConfig = musicUrlConfig;
    }

    @Override
    public UnifiedSearchResponse searchMusic(String songName, String source) {
        return switch (source.toLowerCase()) {
            case "wyy" -> commonSearch("wyy", songName, Map.of("gm", songName), this::mapWYYMusicItem);
            case "kw" -> commonSearch("kw", songName, Map.of(), this::mapKWMusicItem);
            case "qishui" -> commonSearch("qishui", songName, Map.of(), this::mapQishuiMusicItem);
            default -> commonSearch("qq", songName, Map.of(), this::mapQQMusicItem);
        };
    }

    @Override
    public UnifiedDetailResponse getMusicDetail(String songName, String source, String br, Integer n) {
        return switch (source.toLowerCase()) {
            case "wyy" -> commonDetail("wyy", songName, br, n, this::mapWYYMusicDetail);
            case "kw" -> commonDetail("kw", songName, br, n, this::mapKWMusicDetail);
            case "qishui" -> commonDetail("qishui", songName, null, n, this::mapQishuiMusicDetail);
            default -> commonDetail("qq", songName, br, n, this::mapQQMusicDetail);
        };
    }

    /**
     *  通用搜索方法
     * @param source 来源
     * @param songName 歌曲名
     * @param extraParams 额外参数
     * @param mapper 映射函数
     * @return 搜索结果
     */
    private UnifiedSearchResponse commonSearch(String source, String songName, Map<String, Object> extraParams, Function<Map<String, Object>, UnifiedMusicSearchItem> mapper) {
        try {
            String url = getUrlBySource(source);
            String key = getKeyBySource(source);

            Map<String, Object> params = new HashMap<>();
            if (key != null) params.put("key", key);
            if(songName != null && !source.equals("wyy")) params.put("msg", songName);
            if (songName != null && source.equals("wyy")) params.put("gm", songName);
            params.putAll(extraParams);
            params.put("type", "json");
            params.put("num", 60);
            log.info("搜索音乐参数: {}", params);
            Map<String, Object> response = webClient.get()
                    .uri(buildUrl(url, params))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();

            List<Map<String, Object>> dataList = (List<Map<String, Object>>) response.get("data");
            List<UnifiedMusicSearchItem> musicItems = dataList.stream()
                    .map(mapper)
                    .collect(Collectors.toList());
            log.info("搜索音乐结果: {}", dataList);
            return UnifiedSearchResponse.builder()
                    .code(safeGetInteger(response, "code"))
                    .data(musicItems)
                    .source(source)
                    .build();

        } catch (Exception e) {
            log.error("搜索{}音乐失败: {}", source, e.getMessage(), e);
            throw new RuntimeException("搜索" + source + "音乐失败: " + e.getMessage());
        }
    }

    /**
     * 通用详情方法
     * @param source  来源
     * @param songName 歌曲名
     * @param br 比特率
     * @param n  数量
     * @param mapper 映射函数
     * @return 详情结果
     */
    private UnifiedDetailResponse commonDetail(String source, String songName, String br, Integer n, Function<Map<String, Object>, UnifiedMusicItem> mapper) {
        try {
            String url = getUrlBySource(source);
            String key = getKeyBySource(source);

            Map<String, Object> params = new HashMap<>();
            if(songName != null && !source.equals("wyy")) params.put("msg", songName);
            if (songName != null && source.equals("wyy")) params.put("gm", songName);
            if (key != null) params.put("key", key);
            params.put("type", "json");
            if (br != null) params.put("br", br);
            params.put("num", 60);
            params.put("n", n != null ? n : 1);

            Map<String, Object> response = webClient.get()
                    .uri(buildUrl(url, params))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();
            log.info("获取音乐响应: {}", response);
            UnifiedMusicItem musicItem;
            if(source.equals("qq") || source.equals(" qishui")){
                Map<String, Object> innerResponse = (Map<String, Object>) response.get("data");
                musicItem = mapper.apply(innerResponse);
            }else{
                musicItem = mapper.apply(response);
            }

            log.info("获取{}音乐详情成功: {}", source, musicItem);

            return UnifiedDetailResponse.builder()
                    .code(safeGetInteger(response, "code"))
                    .data(musicItem)
                    .source(source)
                    .build();
        } catch (Exception e) {
            log.error("获取{}音乐详情失败: {}", source, e.getMessage(), e);
            throw new RuntimeException("获取" + source + "音乐详情失败: " + e.getMessage());
        }
    }

    // 各平台搜索结果映射
    private UnifiedMusicSearchItem mapQQMusicItem(Map<String, Object> item) {
        return UnifiedMusicSearchItem.builder()
                .n(safeGetInteger(item, "n"))
                .title(safeGetString(item, "song_title", "title", "name"))
                .singer(safeGetString(item, "song_singer", "singer", "artist"))
                .id(safeGetString(item, "songid", "id", "n"))
                .source("qq")
                .build();
    }

    private UnifiedMusicSearchItem mapWYYMusicItem(Map<String, Object> item) {
        return UnifiedMusicSearchItem.builder()
                .n(safeGetInteger(item, "n"))
                .title(safeGetString(item, "title"))
                .singer(safeGetString(item, "singer"))
                .source("wyy")
                .build();
    }

    private UnifiedMusicSearchItem mapKWMusicItem(Map<String, Object> item) {
        return UnifiedMusicSearchItem.builder()
                .n(safeGetInteger(item, "n"))
                .title(safeGetString(item, "songname", "title"))
                .singer(safeGetString(item, "singer"))
                .id(safeGetString(item, "song_rid", "id"))
                .source("kw")
                .build();
    }

    private UnifiedMusicSearchItem mapQishuiMusicItem(Map<String, Object> item) {
        return UnifiedMusicSearchItem.builder()
                .n(safeGetInteger(item, "n"))
                .title(safeGetString(item, "title"))
                .singer(safeGetString(item, "singer"))
                .id(safeGetString(item, "track_id", "n"))
                .source("qishui")
                .build();
    }

    // 各平台详情结果映射
    private UnifiedMusicItem mapQQMusicDetail(Map<String, Object> data) {
        return UnifiedMusicItem.builder()
                .n(safeGetInteger(data, "n"))
                .title(safeGetString(data, "song_name"))
                .singer(safeGetString(data, "song_singer"))
                .id(safeGetString(data, "songid", safeGetString(data, "n")))
                .quality(safeGetString(data, "quality"))
                .cover(safeGetString(data, "cover"))
                .link(safeGetString(data, "link"))
                .musicUrl(safeGetString(data, "music_url"))
                .lyric(safeGetString(data, "lyric"))
                .source("qq")
                .build();
    }

    private UnifiedMusicItem mapWYYMusicDetail(Map<String, Object> data) {
        return UnifiedMusicItem.builder()
                .n(safeGetInteger(data, "n"))
                .title(safeGetString(data, "title"))
                .singer(safeGetString(data, "singer"))
                .id(safeGetString(data, "id", safeGetString(data, "n")))
                .quality(safeGetString(data, "id"))
                .cover(safeGetString(data, "cover"))
                .link(safeGetString(data, "link"))
                .musicUrl(safeGetString(data, "music_url"))
                .lyric(safeGetString(data, "lrc"))
                .source("wyy")
                .build();
    }

    private UnifiedMusicItem mapKWMusicDetail(Map<String, Object> data) {
        return UnifiedMusicItem.builder()
                .n(safeGetInteger(data, "n"))
                .title(safeGetString(data, "song_name"))
                .singer(safeGetString(data, "song_singer"))
                .id(safeGetString(data, "n"))
                .cover(safeGetString(data, "cover"))
                .link(safeGetString(data, "link"))
                .musicUrl(safeGetString(data, "flac_url", "music_url"))
                .source("kw")
                .build();
    }

    private UnifiedMusicItem mapQishuiMusicDetail(Map<String, Object> data) {
        return UnifiedMusicItem.builder()
                .n(safeGetInteger(data, "n"))
                .title(safeGetString(data, "title"))
                .singer(safeGetString(data, "singer"))
                .id(safeGetString(data, "track_id", safeGetString(data, "n")))
                .cover(safeGetString(data, "cover"))
                .link(safeGetString(data, "link"))
                .musicUrl(safeGetString(data, "music"))
                .lyric(safeGetString(data, "lrc"))
                .source("qishui")
                .build();
    }

    /**
     *  获取URL
     * @param source 平台名称
     * @return  URL
     */
    private String getUrlBySource(String source) {
        return switch (source.toLowerCase()) {
            case "wyy" -> musicUrlConfig.getWangyiMusicUrl().getUrl();
            case "kw" -> musicUrlConfig.getKuWoMusicUrl().getUrl();
            case "qishui" -> musicUrlConfig.getQishuiMusicUrl().getUrl();
            default -> musicUrlConfig.getQqMusicUrl().getUrl();
        };
    }

    /**
     * 来源映射
     *
     * @param source 平台名称
     * @return 来源
     */
    private String getKeyBySource(String source) {
        return switch (source.toLowerCase()) {
            case "wyy" -> musicUrlConfig.getWangyiMusicUrl().getKey();
            case "kw" -> musicUrlConfig.getKuWoMusicUrl().getKey();
            case "qishui" -> musicUrlConfig.getQishuiMusicUrl().getKey();
            default -> musicUrlConfig.getQqMusicUrl().getKey();
        };
    }

    /**
     * 构建请求URL
     *
     * @param baseUrl 基础URL
     * @param params  参数
     * @return 构建后的URL
     */
    private String buildUrl(String baseUrl, Map<String, Object> params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl);
        params.forEach((key, value) -> {
            if (value != null) builder.queryParam(key, value.toString());
        });
        // 禁用编码
        return builder.build(false).toUriString();
    }
}