package com.hongchu.hcutils.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongchu.pojo.dto.UserDTO;
import com.hongchu.pojo.entity.User;
import com.hongchu.pojo.vo.UserInfoVO;

public interface UserService extends IService<User> {
    // 获取用户信息
    UserInfoVO getUserInfoByNick(String nickname);
    // 获取用户信息
    UserInfoVO getUserInfo(String param);
    // 登录-参数猜想+密码(用户名/手机号/邮箱)
    void loginByPsd(String param, String password);
    // 注册用户
    void register(String username, String password);
    // 根据邮箱注册用户
    void registerByEmail(String email, String password, String verifyCode);
    // 更改用户名username
    void updateUsername(Long id, String username,String password,String verifyCode);
    // 忘记密码-根据邮箱验证
    void updatePasswordByEmail(String email, String password, String verifyCode);
    // 修改密码
    void updatePassword(String username, String oldPassword, String newPassword);
    // 修改用户信息
    void updateUserInfo(UserDTO userDTO);
    // 注销用户
    void deleteUser(Long id);
}
