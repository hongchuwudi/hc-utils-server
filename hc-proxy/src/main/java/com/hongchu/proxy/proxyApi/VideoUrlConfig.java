package com.hongchu.proxy.proxyApi;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "my-video")
public class VideoUrlConfig {
    private Map<String, VideoSource> sources;

    /**
     * 根据视频源标识获取视频源配置
     * @param sourceKey 视频源标识（如feifan、baofeng等）
     * @return 视频源配置，如果不存在则返回null
     */
    public VideoSource getVideoSource(String sourceKey) {
        return sources != null ? sources.get(sourceKey) : null;
    }

    /**
     * 获取所有可用的视频key名称数组
     * @return 视频源key名称数组
     */
    public String[] getAllVideoSourceKeyNames() {
        if (sources == null || sources.isEmpty()) return new String[0];
        return sources.keySet().toArray(new String[0]);
    }

    @Data
    @Accessors(chain = true) // 启用链式调用
    public static class VideoSource {
        private String url;
        private String name;
    }
}