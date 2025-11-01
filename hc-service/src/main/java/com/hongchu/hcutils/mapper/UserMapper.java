package com.hongchu.hcutils.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongchu.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 自定义更新
    void myUpdateUserById(User user);
}