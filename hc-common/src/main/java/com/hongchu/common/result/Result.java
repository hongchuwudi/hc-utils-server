package com.hongchu.common.result;

import lombok.Data;
import java.io.Serializable;

/**
 * 统一API响应格式
 */
@Data
public class Result<T> implements Serializable {
    
    private Integer code;       // 状态码
    private String message;     // 提示信息
    private T data;             // 响应数据
    private Long timestamp;     // 时间戳
    private String path;        // 请求路径（可选）
    
    // 私有构造器
    private Result() {
        this.timestamp = System.currentTimeMillis();
    }
    
    // 成功响应 - 无数据
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        return result;
    }
    
    // 成功响应 - 有数据
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }
    
    // 失败响应
    public static <T> Result<T> fail(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    
    public static <T> Result<T> fail(ResultCode resultCode) {
        Result<T> result = new Result<>();
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMessage());
        return result;
    }
    
    // 链式调用支持
    public Result<T> code(Integer code) {
        this.code = code;
        return this;
    }
    
    public Result<T> message(String message) {
        this.message = message;
        return this;
    }
    
    public Result<T> data(T data) {
        this.data = data;
        return this;
    }
}