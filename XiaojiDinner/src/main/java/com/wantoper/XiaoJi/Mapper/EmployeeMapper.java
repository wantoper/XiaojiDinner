package com.wantoper.XiaoJi.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wantoper.XiaoJi.Bean.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

    @Select("select * from employee where username= #{username} and password=#{password}")
    Employee login(String username,String password);
}
