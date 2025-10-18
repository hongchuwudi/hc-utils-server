package com.hongchu.common.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiCallLogAnnotation {
    String apiName() default "网络接口";     // 接口名称
    String description() default "";        // 接口描述
    boolean logRequest() default false;      // 是否记录请求参数
    boolean logResponse() default false;     // 是否记录响应数据
    boolean logException() default false;    // 是否记录异常信息
}