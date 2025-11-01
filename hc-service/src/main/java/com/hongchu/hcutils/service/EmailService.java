package com.hongchu.hcutils.service;

public interface EmailService {
    void sendEmailVerifyCode(String email, String businessType);
}
