package com.hongchu.pojo.proxy.bilibili.search;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

// 1. 主响应类
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BilibiliSearchResponse {
    private Integer code;
    private String message;
    private Integer ttl;
    private SearchData data;
    
    public boolean isSuccess() {
        return code != null && code == 0;
    }
}