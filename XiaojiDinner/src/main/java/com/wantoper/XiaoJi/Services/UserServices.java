package com.wantoper.XiaoJi.Services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wantoper.XiaoJi.Bean.User;
import org.springframework.stereotype.Service;

@Service
public interface UserServices extends IService<User> {

    User login(String phone,String password);
}
