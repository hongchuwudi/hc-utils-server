// RegexConstants.java
package com.hongchu.common.constant;

/**
 * 正则表达式常量类
 * 集中管理所有正则表达式，便于维护和复用
 */
public final class RegexConstants {
    
    private RegexConstants() {
        throw new AssertionError("不能实例化常量类");
    }

    // ==================== 身份验证相关 ====================
    
    /**
     * 用户名：6-20位，字母数字下划线，以字母开头
     */
    public static final String USERNAME = "^[a-zA-Z][a-zA-Z0-9_]{3,19}$";
    
    /**
     * 密码：6-18位，必须包含字母和数字，可选特殊字符
     */
    public static final String PASSWORD = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z0-9@#$%^&*!~_-]{6,18}$";
    
    /**
     * 强密码：8-20位，必须包含大小写字母和数字
     */
    public static final String STRONG_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d@#$%^&*!~_-]{8,20}$";

    // ==================== 联系方式相关 ====================
    
    /**
     * 手机号（中国）：1开头，11位数字
     */
    public static final String PHONE_CN = "^1[3-9]\\d{9}$";
    
    /**
     * 手机号（国际格式）：+国家码 手机号
     */
    public static final String PHONE_INTERNATIONAL = "^\\+\\d{1,4}\\s\\d{5,15}$";
    
    /**
     * 邮箱：标准邮箱格式
     */
    public static final String EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    
    /**
     * QQ号：5-12位数字
     */
    public static final String QQ = "^[1-9]\\d{4,11}$";

    // ==================== 身份标识相关 ====================
    
    /**
     * 身份证号（中国）：15位或18位
     */
    public static final String ID_CARD = "(^\\d{15}$)|(^\\d{17}([0-9]|X|x)$)";
    
    /**
     * 护照：字母开头，7-8位字母数字组合
     */
    public static final String PASSPORT = "^[a-zA-Z][a-zA-Z0-9]{6,7}$";

    // ==================== 数字相关 ====================
    
    /**
     * 正整数
     */
    public static final String POSITIVE_INTEGER = "^[1-9]\\d*$";
    
    /**
     * 非负整数（包含0）
     */
    public static final String NON_NEGATIVE_INTEGER = "^\\d+$";
    
    /**
     * 金额：支持小数点后2位
     */
    public static final String MONEY = "^\\d+(\\.\\d{1,2})?$";
    
    /**
     * 百分比：0-100，支持2位小数
     */
    public static final String PERCENTAGE = "^(100|\\d{1,2}(\\.\\d{1,2})?)$";

    // ==================== 网络相关 ====================
    
    /**
     * IP地址：IPv4格式
     */
    public static final String IPV4 = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$";
    
    /**
     * URL：http/https协议
     */
    public static final String URL = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$";
    
    /**
     * 域名：标准域名格式
     */
    public static final String DOMAIN = "^([a-zA-Z0-9]([a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,}$";

    // ==================== 日期时间相关 ====================
    
    /**
     * 日期：yyyy-MM-dd
     */
    public static final String DATE = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
    
    /**
     * 时间：HH:mm:ss
     */
    public static final String TIME = "^([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$";
    
    /**
     * 日期时间：yyyy-MM-dd HH:mm:ss
     */
    public static final String DATETIME = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])\\s([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$";

    // ==================== 文件相关 ====================
    
    /**
     * 文件名：支持中文、字母、数字、下划线、短横线
     */
    public static final String FILENAME = "^[\\u4e00-\\u9fa5a-zA-Z0-9_.-]+$";
    
    /**
     * 图片文件：常见图片格式
     */
    public static final String IMAGE_FILE = "(?i).+\\.(jpg|jpeg|png|gif|bmp|webp)$";
    
    /**
     * 文档文件：常见文档格式
     */
    public static final String DOCUMENT_FILE = "(?i).+\\.(pdf|doc|docx|xls|xlsx|ppt|pptx|txt)$";

    // ==================== 业务特定相关 ====================
    
    /**
     * 颜色代码：16进制颜色
     */
    public static final String HEX_COLOR = "^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$";
    
    /**
     * 版本号：x.x.x格式
     */
    public static final String VERSION = "^\\d+(\\.\\d+){1,2}$";
    
    /**
     * 邮政编码（中国）：6位数字
     */
    public static final String POSTAL_CODE = "^[1-9]\\d{5}$";

    // ==================== 工具方法 ====================
    
    /**
     * 检查字符串是否匹配正则
     * @param input 输入字符串
     * @param regex 正则表达式
     * @return 是否匹配
     */
    public static boolean matches(String input, String regex) {
        return input != null && input.matches(regex);
    }
    
    /**
     * 验证手机号（自动识别国内和国际格式）
     */
    public static boolean isPhone(String input) {
        return matches(input, PHONE_CN) || matches(input, PHONE_INTERNATIONAL);
    }
    
    /**
     * 验证邮箱
     */
    public static boolean isEmail(String input) {
        return matches(input, EMAIL);
    }
    
    /**
     * 验证用户名
     */
    public static boolean isUsername(String input) {
        return matches(input, USERNAME);
    }
}