package com.wantoper.XiaoJi.Controller.Pulic;

import com.wantoper.XiaoJi.Bean.Employee;
import com.wantoper.XiaoJi.Bean.R;
import com.wantoper.XiaoJi.Config.JWTConfig;
import com.wantoper.XiaoJi.Services.EmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private EmployeeServices employeeServices;

    @Autowired
    private JWTConfig jwtConfig;

    @RequestMapping("/adminlogin")
    public R login(@RequestBody Map<String, String> login){
        String username=login.get("username");
        String password= DigestUtils.md5DigestAsHex(login.get("password").getBytes());

        Employee user = employeeServices.login(username, password);
        if(user != null){

            user.setPassword("");
            Map<String,Object> objects = new HashMap();
            objects.put("userinfo",user);
            objects.put("token", jwtConfig.createToken(username));
            return R.success(objects);
        }

        return R.error("登陆失败,账号或密码错误");
    }
}
