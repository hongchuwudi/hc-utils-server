package com.hongchu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 用户信息视图对象
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {
    Boolean isVip = false;
    private Long id;                        // 主键
    private String nickname;                // 昵称
    private String username;                // 用户名
    private String email;                   // 邮箱
    private String phone;                   // 手机号
    private String avatar;                  // 头像
    private java.time.LocalDate birthday;   // 生日
    private Integer gender;                 // 性别
    private String bio;                     // 简介
    private Integer updateUnTimes;          // 剩余更改username的次数
    private LocalDateTime createdAt;        // 创建时间
    private LocalDateTime updatedAt;        // 更新时间
}
