package com.hongchu.pojo.proxy.bilibili;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BilibiliQrcodePollRes {
    private Integer code;
    private String message;
    private Integer ttl;
    private PollData data;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PollData {
        private String url;
        private String refresh_token;
        private Long timestamp;
        private Integer code;  // 状态码：0成功,86038失效,86090已扫码,86101未扫码
        private String message; // 状态信息
    }
}
