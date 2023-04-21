package com.wantoper.XiaoJi.Services.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wantoper.XiaoJi.Bean.User;
import com.wantoper.XiaoJi.Mapper.UserMapper;
import com.wantoper.XiaoJi.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServicesImpl extends ServiceImpl<UserMapper, User> implements UserServices {
    @Autowired UserMapper userMapper;
    public User login(String phone, String password){
        return userMapper.login(phone,password);
    }
}
