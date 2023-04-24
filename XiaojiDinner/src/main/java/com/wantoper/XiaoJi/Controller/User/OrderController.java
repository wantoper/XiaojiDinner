package com.wantoper.XiaoJi.Controller.User;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wantoper.XiaoJi.Bean.*;
import com.wantoper.XiaoJi.Config.JWTConfig;
import com.wantoper.XiaoJi.Services.DishServices;
import com.wantoper.XiaoJi.Services.OrderDetailsServices;
import com.wantoper.XiaoJi.Services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/order")
public class OrderController{
    @Autowired
    private OrderServices orderServices;
    @Autowired
    private OrderDetailsServices orderDetailsServices;
    @Autowired
    private JWTConfig jwtConfig;

    @PostMapping("/createOrder")
    public R createorder(@RequestBody OrderAndOrderDetails orderAndOrderDetails, HttpServletRequest request){
        String userid = jwtConfig.getTokenClaim(request.getHeader("token")).getSubject().toString();
        Orders orders = new Orders();
        orders.setAmount(orderAndOrderDetails.getTotalPrice());
        orders.setRemark(orderAndOrderDetails.getRemark());
        orders.setTableNo(orderAndOrderDetails.getDeskNo());
        orders.setUserId(userid);
        orderServices.save(orders);
        List<orderDetail> order = orderAndOrderDetails.getOrder();
        for (orderDetail orderDetail : order) {
            orderDetail.setDishId(orderDetail.getId());
            orderDetail.setId("");
            orderDetail.setOrderId(orders.getId());
        }
        orderDetailsServices.saveBatch(order);
        return R.success(orders.getId());
    }

    @GetMapping("/Details/{id}")
    public R getorder(@PathVariable String id){
        System.out.println(id);

        return R.success("1");
    }
}
