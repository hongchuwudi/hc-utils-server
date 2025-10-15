package com.hongchu.pojo.proxy.bilibili.search;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

// 4. 搜索结果类
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SearchResult {
    private String resultType;
    private List<Object> data; // 或者根据resultType定义具体类型
}