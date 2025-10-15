package com.hongchu.pojo.proxy.bilibili;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BilibiliVideoPlayRequest {
    private String bvid;
    private Long avid;

    @NotNull(message = "cid不能为空")
    private Long cid;

    private String  platform;
    private Integer qn;
    private Integer fnval;
    private Integer fourk;
    private String  token;
    private Integer fnver;

}