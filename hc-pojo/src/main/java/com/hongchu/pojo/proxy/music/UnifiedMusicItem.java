// UnifiedMusicItem.java
package com.hongchu.pojo.proxy.music;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnifiedMusicItem {
    private Integer n;
    private String title;
    private String singer;
    private String id;
    private String quality;
    private String cover;
    private String link;
    private String musicUrl;
    private String lyric;
    private String source;
    private String lyricUrl;
}
