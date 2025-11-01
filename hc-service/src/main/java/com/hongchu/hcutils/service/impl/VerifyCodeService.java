// VerifyCodeService.java
package com.hongchu.hcutils.service.impl;

import com.hongchu.common.cache.ManualCacheManager;
import com.hongchu.common.constant.EmailConstants;
import com.hongchu.common.constant.ErrorMessageConstants;
import com.hongchu.common.exception.BusinessException;
import com.hongchu.common.util.EmailUtil;
import com.hongchu.hcutils.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.hongchu.common.util.EmailUtil.getBusinessTypeName;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerifyCodeService implements EmailService {

    private final ManualCacheManager cacheManager;
    private final EmailUtil emailUtil;

    private static final String CACHE_PREFIX = "verify:code:";

    // 业务类型常量
    public static final String BUSINESS_LOGIN = "login";
    public static final String BUSINESS_REGISTER = "register";
    public static final String BUSINESS_RESET_PWD = "reset_pwd";
    public static final String BUSINESS_CHANGE_EMAIL = "change_email";
    public static final String BUSINESS_CHANGE_PHONE = "change_phone";
    public static final String BUSINESS_CHANGE_USERNAME = "change_username";
    public static final String BUSINESS_FORGET_PWD = "forget_pwd";

    /**
     * 发送邮箱验证码 - 支持不同业务场景
     */
    public void sendEmailVerifyCode(String email, String businessType) {
        // 1. 邮箱格式校验
        if (!emailUtil.isValidEmail(email))
            throw new BusinessException(ErrorMessageConstants.ERROR_EMAIL_FORMAT);

        // 2. 生成验证码
        String verifyCode = emailUtil.generateVerifyCode();

        // 3. 发送邮件（根据业务类型定制）
        emailUtil.sendVerifyCodeEmail(email, verifyCode, businessType);

        // 4. 保存到缓存（包含业务类型）
        saveVerifyCode(email, verifyCode, businessType);

        log.info("{}验证码发送成功，邮箱：{}", getBusinessTypeName(businessType), email);
    }

    /**
     * 保存验证码到缓存 - 支持业务类型
     */
    public void saveVerifyCode(String email, String verifyCode, String businessType) {
        String cacheKey = buildCacheKey(email, businessType);
        cacheManager.put(cacheKey, verifyCode, EmailConstants.VERIFY_CODE_EXPIRE_MINUTES, TimeUnit.MINUTES);
        log.debug("{}验证码保存到缓存，邮箱：{}", getBusinessTypeName(businessType), email);
    }

    /**
     * 获取验证码 - 支持业务类型
     */
    public String getVerifyCode(String email, String businessType) {
        String cacheKey = buildCacheKey(email, businessType);
        String verifyCode = cacheManager.get(cacheKey);

        if (verifyCode == null) {
            log.debug("{}验证码不存在或已过期，邮箱：{}", getBusinessTypeName(businessType), email);
        } else {
            log.debug("获取{}验证码成功，邮箱：{}", getBusinessTypeName(businessType), email);
        }

        return verifyCode;
    }

    /**
     * 验证验证码 - 支持业务类型
     */
    public boolean verifyCode(String email, String inputCode, String businessType) {
        String storedCode = getVerifyCode(email, businessType);

        if (storedCode == null) {
            throw new BusinessException(ErrorMessageConstants.ERROR_EMAIL_VERIFY_CODE_EXPIRED);
        }

        boolean isValid = storedCode.equals(inputCode);
        if (isValid) {
            // 验证成功后删除验证码
            deleteVerifyCode(email, businessType);
            log.info("{}验证码验证成功，邮箱：{}", getBusinessTypeName(businessType), email);
        } else {
            log.warn("{}验证码验证失败，邮箱：{}", getBusinessTypeName(businessType), email);
            throw new BusinessException(ErrorMessageConstants.ERROR_EMAIL_VERIFY_CODE_INCORRECT);
        }

        return isValid;
    }

    /**
     * 删除验证码 - 支持业务类型
     */
    public void deleteVerifyCode(String email, String businessType) {
        String cacheKey = buildCacheKey(email, businessType);
        cacheManager.delete(cacheKey);
        log.debug("{}验证码删除成功，邮箱：{}", getBusinessTypeName(businessType), email);
    }

    /**
     * 检查验证码是否存在 - 支持业务类型
     */
    public boolean hasVerifyCode(String email, String businessType) {
        String cacheKey = buildCacheKey(email, businessType);
        return cacheManager.contains(cacheKey);
    }

    /**
     * 构建缓存key
     */
    private String buildCacheKey(String email, String businessType) {
        return CACHE_PREFIX + businessType + ":" + email;
    }
}