package com.hongchu.proxy.service.bilibili;

import com.hongchu.pojo.proxy.bilibili.*;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface BilibiliPassportLoginService {
    /**
     * 获取web端登录二维码
     * @return 登录二维码 + qrcodeKey
     */
    public BilibiliPassportLoginRes getWebLoginQrcode();

    /**
     *  获取web端登录二维码状态
     * @param qrcodeKey 二维码key
     * @return 登录状态
     */
    public Mono<BilibiliQrcodePollRes> getWebLoginQrcodeStatus(String qrcodeKey);

    /**
     * 提取二维码中的图片
     * @return 二维码图片的Base64编码
     */
    public BilibiliQrcodeImage extractQRCodeFromPage();
}
