package com.hongchu.pojo.proxy.bilibili;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BilibiliQrcodeImage {
    private String qrcodeKey;
    private String baseUrl;
}
