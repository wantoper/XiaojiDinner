package com.wantoper.XiaoJi.Controller.Pulic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wantoper.XiaoJi.Bean.Employee;
import com.wantoper.XiaoJi.Bean.R;
import com.wantoper.XiaoJi.Bean.User;
import com.wantoper.XiaoJi.Config.JWTConfig;
import com.wantoper.XiaoJi.Services.EmployeeServices;
import com.wantoper.XiaoJi.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private EmployeeServices employeeServices;
    @Autowired
    private UserServices userServices;
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

    @RequestMapping("/userlogin")
    public R usrlogin(@RequestBody Map<String, String> login){


        String phone=login.get("phone");
        String password= DigestUtils.md5DigestAsHex(login.get("password").getBytes());

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
        userQueryWrapper.eq("phone",phone);
        User one = userServices.getOne(userQueryWrapper);
        if(one == null){
            User user = new User();
            user.setPhone(phone);
            user.setPassword(password);
            user.setStatus(1);
            user.setName("吃货"+phone.substring(phone.length()-4));
            boolean save = userServices.save(user);
            if(save){
                Map<String,Object> objects = new HashMap();
                objects.put("userinfo",user);
                objects.put("token", jwtConfig.createToken(one.getId()));
                return R.success(objects,"首次登录，已自动为您注册！");
            }
        }else{
            if(one.getPassword().equals(password)){
                one.setPassword("");
                Map<String,Object> objects = new HashMap();
                objects.put("userinfo",one);
                objects.put("token", jwtConfig.createToken(one.getId()));
                return R.success(objects,"登录成功,快去下单吧！！");
            }
        }

        return R.error("登陆失败,账号或密码错误");
    }
}
