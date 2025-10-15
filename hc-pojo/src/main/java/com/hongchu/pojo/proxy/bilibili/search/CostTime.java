package com.hongchu.pojo.proxy.bilibili.search;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

// 3. 耗时类
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CostTime {
    private String total;
    private String mainHandler;
    private String paramsCheck;
    private String asRequest;
}