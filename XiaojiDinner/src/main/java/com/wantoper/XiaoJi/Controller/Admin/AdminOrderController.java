package com.wantoper.XiaoJi.Controller.Admin;

import com.wantoper.XiaoJi.Bean.R;
import com.wantoper.XiaoJi.Services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/order")
public class AdminOrderController {

    @Autowired
    private OrderServices orderServices;

    @RequestMapping("/getall")
    public R getall(){
        return R.success(orderServices.list());
    }
}
