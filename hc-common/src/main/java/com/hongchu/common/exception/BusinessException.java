package com.hongchu.common.exception;

/**
 * 全局业务异常 - 一个就够了
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    // 有异常原因时使用
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}