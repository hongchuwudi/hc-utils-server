package com.hongchu.pojo.proxy.bilibili;

import lombok.Data;

/**
 * 获取视频流地址请求参数
 */
@Data
public class PlayUrlRequest {
    private String bvid;        // 稿件bvid，与avid二选一
    private Long avid;          // 稿件avid，与bvid二选一
    private Long cid;           // 视频cid，必填
    private String platform;    // 平台：pc/html5，默认pc
    private Integer qn;         // 视频清晰度标识
    private Integer fnval;      // 视频流格式标识
    private Integer fnver;      // 视频流版本标识，默认0
    private Integer fourk;      // 是否允许4K视频
    private String session;     // 会话标识
    private String otype;       // 输出类型，默认json
    private String type;        // 类型
    private Integer high_quality; // 是否高画质
    private Integer try_look;   // 未登录高画质
}