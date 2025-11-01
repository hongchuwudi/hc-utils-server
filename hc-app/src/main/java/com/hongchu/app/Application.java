package com.hongchu.app;

//import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "com.hongchu")
@MapperScan("com.hongchu.**.mapper")
@EnableCaching  // 启用缓存
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}