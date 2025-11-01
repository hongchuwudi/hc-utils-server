// EmailConstants.java
package com.hongchu.common.constant;

public final class EmailConstants {
    private EmailConstants() {}
    
    // 验证码相关
    public static final int VERIFY_CODE_LENGTH = 6;
    public static final int VERIFY_CODE_EXPIRE_MINUTES = 5;
    public static final String VERIFY_CODE_PREFIX = "verify:code:";
    
    // 邮件模板

    public static final String EMAIL_SUBJECT_VERIFY_CODE = "验证码通知";
    public static final String EMAIL_TEMPLATE_VERIFY_CODE =
            "【HC-utils】尊敬的用户，您好！\n\n" +
                    "您正在进行的操作需要验证身份，本次请求的验证码为：%s\n\n" +
                    "此验证码有效期为%d分钟，请在有效期内完成验证。为了保障您的账户安全，请勿将验证码泄露给任何人，包括我们的客服人员。\n\n" +
                    "如果您并未发起此请求，可能是他人误输了您的邮箱地址，请忽略此邮件。如频繁收到此类邮件，建议您检查账户安全设置。\n\n" +
                    "感谢您使用HC-utils服务！我们将竭诚为您提供优质的使用体验。\n\n" +
                    "祝您使用愉快！\n\n" +
                    "hongchu敬上\n" +
                    "%tF"; // 自动添加当前日期
    
    // Redis Key
    public static final String REDIS_EMAIL_CODE_PREFIX = "email:code:";
    public static final String REDIS_EMAIL_LIMIT_PREFIX = "email:limit:";

    // 发送限制
    public static final int EMAIL_SEND_LIMIT_PER_MINUTE = 1;
    public static final int EMAIL_SEND_LIMIT_PER_HOUR = 5;
    public static final int EMAIL_SEND_LIMIT_PER_DAY = 10;

    public static final String DEFAULT_USERNAME = "emailTempUsername";
}