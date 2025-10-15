// UnifiedDetailResponse.java
package com.hongchu.pojo.proxy.music;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnifiedDetailResponse {
    private Integer          code;   // 状态码
    private UnifiedMusicItem data;   // 数据
    private String           source; // 来源
}