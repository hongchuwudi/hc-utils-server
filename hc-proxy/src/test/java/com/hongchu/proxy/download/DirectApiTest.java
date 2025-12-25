package com.hongchu.proxy.download;

import java.io.*;
import java.net.*;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class DirectApiTest {

    public static void main(String[] args) throws Exception {
        // 方法1：使用已编码的URL（和你的WebClient一致）
        String encodedUrl = "https://bfzyapi.com/api.php/provide/vod/?ac=detail&wd=%E8%AE%A9%E5%AD%90%E5%BC%B9%E9%A3%9E";

        // 方法2：动态编码
        String keyword = "让子弹飞";
        String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8.toString());
        String dynamicUrl = "https://bfzyapi.com/api.php/provide/vod/?ac=detail&wd=" + encodedKeyword;

        System.out.println("编码URL: " + encodedUrl);
        System.out.println("动态编码URL: " + dynamicUrl);
        System.out.println("====================================");

        // 测试编码后的URL
        testUrl(encodedUrl);

        System.out.println("\n\n====================================\n\n");

        // 测试动态编码的URL
        testUrl(dynamicUrl);
    }

    private static void testUrl(String url) throws Exception {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .header("Accept", "*/*")
                .header("Accept-Language", "zh-CN,zh;q=0.9")
                .GET()
                .build();

        System.out.println("请求URL: " + url);

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("响应码: " + response.statusCode());
        System.out.println("响应头:");
        response.headers().map().forEach((k, v) -> System.out.println("  " + k + ": " + v));
        System.out.println("\n响应体:");
        System.out.println(response.body());
    }
}