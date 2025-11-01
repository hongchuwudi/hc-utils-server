package com.hongchu.hcutils.controller;

import com.hongchu.common.result.Result;
import com.hongchu.hcutils.service.UserService;
import com.hongchu.pojo.dto.UserDTO;
import com.hongchu.pojo.entity.User;
import com.hongchu.pojo.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户
 * @author HongChu
 * @deprecated  用于登录,注册,获取信息,修改信息
 */
@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired private UserService userService;

    /**
     * 根据昵称模糊查找用户信息
     */
    @GetMapping("/info-nick")
    public Result<UserInfoVO> getUserInfoByNick(@RequestParam String nickname) {
        log.info("根据NickName用户信息: nickname={}",nickname);
        UserInfoVO user = userService.getUserInfoByNick(nickname);
        return Result.success(user);
    }

    /**
     * 根据参数猜想(ID/手机号/邮箱/账号)查找用户信息
     * ID: 纯数字
     * 手机号: 加法符号开头 + 若干位数字 + 空格 + 11位纯数字
     * 邮箱: 纯字符串 + @ + 纯字符串 + . + 纯字符串
     * 账号: 6-18位 大小写字母 + 符号组合
     */
    @GetMapping("/info-un")
    public Result<UserInfoVO> getUserInfo(@RequestParam String param) {
        log.info("用户信息: 参数猜想param={}", param);
        UserInfoVO user = userService.getUserInfo(param);
        return Result.success(user);
    }

    /**
     * 普通注册用户
     */
    @PostMapping("/register")
    public Result<String> register(
            @RequestParam String username,
            @RequestParam String password) {
        log.info("用户注册: username={}", username);
        userService.register(username, password);
        return Result.success("注册成功");
    }

    /**
     * 邮箱注册用户
     */
    @PostMapping("/register-email")
    public Result<String> registerByEmail(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String verifyCode) {
        log.info("用户注册: username={}", email);
        userService.registerByEmail(email, password,verifyCode);
        return Result.success("注册成功");
    }

    /**
     * 登录-密码
     * 参数猜想+密码(用户名/手机号/邮箱)
     */
    @PostMapping("/login")
    public Result<String> login(
            @RequestParam String param,
            @RequestParam String password) {
        log.info("用户登录: param={}", param);
        userService.loginByPsd(param, password);
        return Result.success("登录成功!");
    }

    /**
     * 重置密码
     */
    @PostMapping("/reset")
    public Result<String> forget(
            @RequestParam String username,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        log.info("用户修改密码: username={}", username);
        userService.updatePassword(username, oldPassword, newPassword);
        return Result.success("修改密码成功!");
    }

    /**
     * 修改用户名
     */
    @PostMapping("/username")
    public Result<String> updateUsername(
            @RequestParam Long id,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String verifyCode) {
        log.info("用户修改用户名: username={}", username);
        userService.updateUsername(id, username, password, verifyCode);
        return Result.success("修改用户名成功!");
    }

    /**
     * 忘记密码
     */
    @PostMapping("/forget-password")
    public Result<String> forgetPassword(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String verifyCode) {
        log.info("用户修改密码: username={}", email);
        userService.updatePasswordByEmail(email, password, verifyCode);
        return Result.success("修改密码成功!");
    }

    /**
     * 修改用户信息
     */
    @PutMapping()
    public Result<String> update(@RequestBody UserDTO userDTO) {
        log.info("用户修改信息: username={}", userDTO.getId());
        userService.updateUserInfo(userDTO);
        return Result.success("修改信息成功!");
    }

    /**
     * 注销用户
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        log.info("用户注销: id={}", id);
        userService.deleteUser(id);
        return Result.success("注销成功!");
    }
}
