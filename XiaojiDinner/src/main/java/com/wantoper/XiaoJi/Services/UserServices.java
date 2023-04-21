package com.wantoper.XiaoJi.Services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wantoper.XiaoJi.Bean.User;

public interface UserServices extends IService<User> {

    User login(String phone,String password);
}
