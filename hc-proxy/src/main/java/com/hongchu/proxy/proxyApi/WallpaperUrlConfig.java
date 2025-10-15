package com.hongchu.proxy.proxyApi;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "my-paper")
@Data
public class WallpaperUrlConfig {
    private String jinShanUrl; // 金山毒霸壁纸
    // ....
}
