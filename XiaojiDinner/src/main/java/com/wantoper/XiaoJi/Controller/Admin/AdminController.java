package com.wantoper.XiaoJi.Controller.Admin;

import com.wantoper.XiaoJi.Bean.Employee;
import com.wantoper.XiaoJi.Bean.R;
import com.wantoper.XiaoJi.Bean.ShopInfo;
import com.wantoper.XiaoJi.Config.JWTConfig;
import com.wantoper.XiaoJi.Mapper.ShopInfoMapper;
import com.wantoper.XiaoJi.Services.EmployeeServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private ShopInfoMapper shopInfoMapper;
    @RequestMapping("/shop/set")
    public R shopset(@RequestBody ShopInfo shopInfo){
        shopInfoMapper.updateById(shopInfo);
        return R.success("修改成功！");
    }
}
