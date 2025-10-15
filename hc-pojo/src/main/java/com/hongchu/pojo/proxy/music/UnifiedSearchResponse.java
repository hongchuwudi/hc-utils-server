// UnifiedSearchResponse.java
package com.hongchu.pojo.proxy.music;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnifiedSearchResponse {
    private Integer code;
    private List<UnifiedMusicSearchItem> data;
    private String source;
}