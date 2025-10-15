package com.hongchu.pojo.proxy.wallpaper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JinShanResponse {
    private Integer code;
    private String msg;
    private List<JinShanWallpaper> data;
    private String tips;
    private String time;
}