package com.hongchu.hcutils.controller;

import com.hongchu.common.result.Result;
import com.hongchu.common.util.EmailUtil;
import com.hongchu.hcutils.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/email")
@CrossOrigin
public class EmailController {
    @Autowired private EmailService emailService;

    /**
     * 发送验证码邮件
     * @param email 邮箱
     * @param type 业务类型：login-登录, register-注册, reset_pwd-重置密码
     * @return 状态码
     */
    @GetMapping("/send-vce")
    public Result<String> sendVerifyCodeEmail(String email, String type) {
        log.info("发送{}验证码邮件...", EmailUtil.getBusinessTypeName(type));
        emailService.sendEmailVerifyCode(email, type);
        return Result.success("发送成功");
    }
}
