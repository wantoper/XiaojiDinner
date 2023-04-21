package com.wantoper.XiaoJi.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wantoper.XiaoJi.Bean.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where username= #{phone} and password=#{password}")
    User login(String phone,String password);
}
