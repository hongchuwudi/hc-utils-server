package com.hongchu.pojo.proxy.music;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnifiedMusicSearchItem {
    private String id;
    private Integer n;
    private String title;
    private String singer;
    private String source;
}
