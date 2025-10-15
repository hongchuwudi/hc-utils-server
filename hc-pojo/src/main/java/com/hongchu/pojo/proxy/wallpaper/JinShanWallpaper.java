package com.hongchu.pojo.proxy.wallpaper;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class JinShanWallpaper {
    private Integer id;            // id
    private String name;          // 名称
    private String cname;         // 壁纸名称
    private String author;        // 作者
    private Integer authorUid;    // 作者id
    private String authorImg;     // 作者头像
    private String format;        // 格式
    private String decs;          // 描述
    private String hot;           // 热度
    private String size;          // 大小
    private String duration;      // 时长
    private String phoneImgUrl;   // 手机壁纸
    private String resolution;    // 分辨率
    private String videoUrl;      // 视频
    private String time;          // 时间
}

