package com.hongchu.proxy.controller.bilibili;

import com.hongchu.common.result.Result;
import com.hongchu.pojo.proxy.bilibili.BilibiliPassportLoginRes;
import com.hongchu.pojo.proxy.bilibili.BilibiliQrcodeImage;
import com.hongchu.pojo.proxy.bilibili.BilibiliQrcodePollRes;
import com.hongchu.proxy.service.bilibili.BilibiliPassportLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * B站二维码登录
 * @author HongChu
 * @date 2023/10/21
 */
@Slf4j
@RestController
@RequestMapping("/proxy/bilibili")
public class BilibiliPassportLoginController {
    private final BilibiliPassportLoginService bilibiliPassportLoginService;

    public BilibiliPassportLoginController(BilibiliPassportLoginService bilibiliPassportLoginService) {
        this.bilibiliPassportLoginService = bilibiliPassportLoginService;
    }

    /**
     * web端申请二维码(哔站官网登录页面)
     * @return 登录页面 + qrcodeKey
     */
    @RequestMapping("/passport/login")
    public BilibiliPassportLoginRes getQrcode() {
        log.info("web端扫码登录-申请二维码...");
        return bilibiliPassportLoginService.getWebLoginQrcode();
    }

    /**
     * web端只获取登录二维码图片
     * @return 二维码图片 + qrcodeKey
     */
    @RequestMapping("/passport/login/only")
    public Result<BilibiliQrcodeImage> getQrcodeImage() {
        log.info("web端扫码登录-获取二维码图片...");
        BilibiliQrcodeImage BQrcodeI = bilibiliPassportLoginService.extractQRCodeFromPage();
        return Result.success(BQrcodeI);
    }

    /**
     * web端登录状态-非阻塞(用于前端轮询)
     * @param qrcodeKey 二维码key
     * @return 登录状态
     */
    @RequestMapping("/passport/login/status")
    public Mono<BilibiliQrcodePollRes> getLoginStatus(@RequestParam("qrcode_key") String qrcodeKey) {
        log.info("web端扫码登录-获取登录状态,qrcodeKey:{}", qrcodeKey);
        return bilibiliPassportLoginService.getWebLoginQrcodeStatus(qrcodeKey);
    }
}
