package com.hongchu.pojo.proxy.bilibili.search;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.naming.directory.SearchResult;
import java.util.List;
import java.util.Map;

// 2. 数据类
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SearchData {
    private String seid;
    private Integer page;
    private Integer pageSize;
    private Integer numResults;
    private Integer numPages;
    private String suggestKeyword;
    private String rqtType;
    private CostTime costTime;
    private List<SearchResult> result;
    private Map<String, Object> pageInfo;
    private Map<String, Integer> topTlist;
    private List<String> showModuleList;
}