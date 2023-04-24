package com.wantoper.XiaoJi.Controller.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wantoper.XiaoJi.Bean.Dish;
import com.wantoper.XiaoJi.Bean.OrderAndOrderDetails;
import com.wantoper.XiaoJi.Bean.Orders;
import com.wantoper.XiaoJi.Bean.R;
import com.wantoper.XiaoJi.Services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/order")
public class OrderController{
    @Autowired
    private OrderServices orderServices;

    @PostMapping("/createOrder")
    public R createorder(@RequestBody OrderAndOrderDetails orderAndOrderDetails){
        Orders orders = new Orders();
        orders.setAmount(orderAndOrderDetails.getTotalPrice());
        orders.setRemake(orderAndOrderDetails.getRemark());
        orders.setTableNo(orderAndOrderDetails.getDeskNo());
        System.out.println(orderAndOrderDetails.getOrder());
        return R.success("1");
    }
}
