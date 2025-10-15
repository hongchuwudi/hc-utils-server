package com.hongchu.proxy.service.impl.bilibili;

import com.hongchu.common.util.QRCodeUtil;
import com.hongchu.pojo.proxy.bilibili.BilibiliPassportLoginRes;
import com.hongchu.pojo.proxy.bilibili.BilibiliQrcodeImage;
import com.hongchu.pojo.proxy.bilibili.BilibiliQrcodePollRes;
import com.hongchu.proxy.proxyApi.BilibiliUrlConfig;
import com.hongchu.proxy.service.bilibili.BilibiliPassportLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.Duration;

@Service
@Slf4j
public class BilibiliPassportLoginServiceImpl implements BilibiliPassportLoginService {

    private final WebClient         webClient; // WebClient实例
    private final BilibiliUrlConfig urlconfig; // BilibiliUrlConfig实例
    public BilibiliPassportLoginServiceImpl(WebClient webClient, BilibiliUrlConfig bilibiliUrlConfig) {
        this.webClient = webClient;
        this.urlconfig = bilibiliUrlConfig;
    }

    /**
     * 获取web端登录二维码
     * @return 登录二维码 + qrcodeKey
     */
    @Override
    public BilibiliPassportLoginRes getWebLoginQrcode(){
        log.info("获取B站web端登录二维码");

        return webClient.get()
                .uri(urlconfig.getLogin()) // 请求地址
                .retrieve() // 获取响应
                .bodyToMono(BilibiliPassportLoginRes.class) // 转换为字符串
                .block();
    }

    /**
     * 提取二维码中的图片
     * @return 二维码图片的Base64编码
     */
    @Override
    public BilibiliQrcodeImage extractQRCodeFromPage() {
        BilibiliPassportLoginRes webLoginQrcode = getWebLoginQrcode();

        // 找到二维码登录页面url
        String qrcodeUrl = webLoginQrcode.getData().getUrl();
        String qrcodeKey = webLoginQrcode.getData().getQrcode_key();

        // 使用Google Chart API生成二维码图片（B站实际使用的方式）
        String qrCodeBase64 = QRCodeUtil.generateQRCodeBase64(qrcodeUrl);

        // 封装二维码图片数据
        return BilibiliQrcodeImage.builder()
                .baseUrl(qrCodeBase64)
                .qrcodeKey(qrcodeKey)
                .build();
    }

    /**
     * 获取web端登录二维码状态
     * @param qrcodeKey 二维码key
     * @return 登录状态
     */
    @Override
    public Mono<BilibiliQrcodePollRes> getWebLoginQrcodeStatus(String qrcodeKey) {
        log.info("获取web端登录二维码状态...");

        String url = urlconfig.getLoginStatus() + "?"
                + "qrcode_key=" + qrcodeKey;

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(BilibiliQrcodePollRes.class)
                .timeout(Duration.ofSeconds(5))
                .doOnError(error -> log.error("查询登录状态失败: {}", error.getMessage()));
    }

}
