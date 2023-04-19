package com.wantoper.XiaoJi.Services.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wantoper.XiaoJi.Bean.Employee;
import com.wantoper.XiaoJi.Mapper.EmployeeMapper;
import com.wantoper.XiaoJi.Services.EmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServicesImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeServices {

    @Autowired
    private EmployeeMapper mapper;


    public Employee login(String username,String password) {
        return mapper.login(username,password);
    }

}
