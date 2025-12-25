// ApiResponse.java
package com.hongchu.pojo.proxy.video;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 统一API响应结构
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private Integer code;
    @JsonProperty("msg")private String message;
    private Integer page;
    @JsonProperty("pagecount") private Integer pageCount;
    private Integer limit;
    private Long total;
    private List<Object> list;
    @JsonProperty("class") private List<Object> classes;
}