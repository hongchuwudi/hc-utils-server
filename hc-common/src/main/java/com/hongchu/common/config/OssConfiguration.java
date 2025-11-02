package com.hongchu.common.config;

import com.hongchu.common.properties.AliOssProperties;
import com.hongchu.common.util.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类,用于创建AliOssUtil对象
 */
@Slf4j
@Configuration
public class OssConfiguration {

    @Bean                           //  一开始就被IOC容器执行存储
    @ConditionalOnMissingBean       //  保证容器中只有一个util对象 ,当没有bean的时候再去创建
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){
        //  创建一个阿里云oss文件上传对象并返回
        log.info("开始创建阿里云文件上传工具对象,{}",aliOssProperties);
        return new AliOssUtil(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName());
    }
}