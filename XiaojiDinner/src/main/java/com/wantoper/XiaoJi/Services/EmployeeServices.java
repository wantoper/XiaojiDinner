package com.wantoper.XiaoJi.Services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wantoper.XiaoJi.Bean.Employee;

public interface EmployeeServices extends IService<Employee> {

    Employee login(String username,String password);
}
