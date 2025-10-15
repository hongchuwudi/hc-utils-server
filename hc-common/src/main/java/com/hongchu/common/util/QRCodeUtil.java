package com.hongchu.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class QRCodeUtil {

    /**
     * 生成二维码Base64图片
     * @param content 二维码内容
     * @param size 图片尺寸
     * @return base64格式的图片数据
     */
    public static String generateQRCodeBase64(String content, int size) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 1);

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, size, size, hints);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

            String base64Image = Base64.getEncoder().encodeToString(outputStream.toByteArray());
            return "data:image/png;base64," + base64Image;

        } catch (Exception e) {
            log.error("生成二维码失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 生成二维码Base64图片（默认尺寸200x200）
     */
    public static String generateQRCodeBase64(String content) {
        return generateQRCodeBase64(content, 200);
    }
}