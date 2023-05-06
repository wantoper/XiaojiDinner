package com.wantoper.XiaoJi.Controller.Admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wantoper.XiaoJi.Bean.*;
import com.wantoper.XiaoJi.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/user")
public class AdminUserController {

    @Autowired
    UserServices userServices;

    @PostMapping("/getall")
    public R getall(@RequestBody Map<String,String> map){
        Page<User> page=new Page<User>(Integer.parseInt(map.get("index")),Integer.parseInt(map.get("size")));

        QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();

        objectQueryWrapper.like("phone",map.getOrDefault("name",""))
         .or()
         .like("id",map.getOrDefault("name",""));

        Page<User> page1 = userServices.page(page, objectQueryWrapper);
        List<User> records = page1.getRecords();

        //去除显示用户密码
        for (User record : records) {
            record.setPassword("");
        }

        Map<String,Object> result = new HashMap<>();
        result.put("data",records);
        result.put("total",page1.getTotal());

        return R.success(result);
    }

    @RequestMapping("/add")
    public R add(@RequestBody User user){
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        try {
            userServices.save(user);
        }catch (Exception e){
            return R.error("添加失败！");
        }
        return R.success("添加成功！");
    }
    @RequestMapping("/edit")
    public R edit(@RequestBody User user){
        System.out.println(user);
        if(user.getPassword() == null || user.getPassword().isEmpty()){
            user.setPassword(null);
        }else {
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        }
        try {
            userServices.updateById(user);
        }catch (Exception e){
            return R.error("添加失败！");
        }
        return R.success("添加成功！");
    }
}
