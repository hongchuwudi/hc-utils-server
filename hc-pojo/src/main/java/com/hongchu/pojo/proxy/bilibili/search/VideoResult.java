package com.hongchu.pojo.proxy.bilibili.search;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

// 5. 视频结果类
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VideoResult {
    private String type;
    private Long id;
    private String author;
    private Long mid;
    private String typeid;
    private String typename;
    private String arcurl;
    private Long aid;
    private String bvid;
    private String title;
    private String description;
    private String arcrank;
    private String pic;
    private Integer play;
    private Integer videoReview;
    private Integer favorites;
    private String tag;
    private Integer review;
    private Long pubdate;
    private Long senddate;
    private String duration;
    private Boolean badgepay;
    private List<String> hitColumns;
    private String viewType;
    private Integer isPay;
    private Integer isUnionVideo;
}