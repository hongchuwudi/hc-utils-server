package com.hongchu.pojo.proxy.bilibili;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BilibiliPassportLoginRes {
    private Integer code;
    private String message;
    private Integer ttl;
    private ResponseData data;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseData {
        private String url;
        private String qrcode_key;
    }
}