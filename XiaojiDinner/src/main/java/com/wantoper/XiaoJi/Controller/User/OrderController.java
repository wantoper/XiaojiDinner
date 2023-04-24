package com.wantoper.XiaoJi.Controller.User;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import java.util.ArrayList;
import java.util.HashMap;
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
        Orders byId = orderServices.getById(id);
        if(byId != null){
            QueryWrapper<orderDetail> objectQueryWrapper = new QueryWrapper<orderDetail>();
            objectQueryWrapper.eq("order_id",id);
            List<orderDetail> list = orderDetailsServices.list(objectQueryWrapper);
            HashMap<String, Object> rs = new HashMap<String, Object>();
            rs.put("dishs",list);
            rs.put("order",byId);
            return R.success(rs);
        }
        return R.error("该订单不存在！");
    }

    @GetMapping("/myorders")
    public R myorders(HttpServletRequest request){
        String userid = jwtConfig.getTokenClaim(request.getHeader("token")).getSubject();

        QueryWrapper<Orders> ordersQueryWrapper = new QueryWrapper<>();
        ordersQueryWrapper.eq("user_id",userid);
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<Orders> list = orderServices.list(ordersQueryWrapper);

        for (Orders orders : list) {
            Map<String, Object> rs = new HashMap<>();

            QueryWrapper<orderDetail> objectQueryWrapper = new QueryWrapper<orderDetail>();
            objectQueryWrapper.eq("order_id",orders.getId());
            List<orderDetail> orderDetaillist = orderDetailsServices.list(objectQueryWrapper);

            rs.put("orderinfo",orders);
            rs.put("orderDetaillist",orderDetaillist);

            resultList.add(rs);
        }

        return R.success(resultList);
    }
}
