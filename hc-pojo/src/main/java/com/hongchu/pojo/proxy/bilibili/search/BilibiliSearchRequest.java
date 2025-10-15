package com.hongchu.pojo.proxy.bilibili.search;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BilibiliSearchRequest {
    private String keyword;
    private String searchType;
    private String order;
    private Integer orderSort;
    private Integer userType;
    private Integer duration;
    private Integer tids;
    private Integer categoryId;
    private Integer page;
    private Integer pageSize;
    
    public static BilibiliSearchRequest of(String keyword) {
        return BilibiliSearchRequest.builder()
                .keyword(keyword)
                .page(1)
                .pageSize(20)
                .build();
    }
}