package com.wantoper.XiaoJi.Controller.Admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wantoper.XiaoJi.Bean.*;
import com.wantoper.XiaoJi.Controller.User.UserController;
import com.wantoper.XiaoJi.Services.OrderDetailsServices;
import com.wantoper.XiaoJi.Services.OrderServices;
import com.wantoper.XiaoJi.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/order")
public class AdminOrderController {

    @Autowired
    private OrderServices orderServices;
    @Autowired
    OrderDetailsServices orderDetailsServices;
    @Autowired
    UserServices userServices;

    @PostMapping("/getallfinish")
    public R getall(@RequestBody Map<String,String> map){
        Page<Orders> page=new Page<Orders>(Integer.parseInt(map.get("index")),Integer.parseInt(map.get("size")));

        QueryWrapper<Orders> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.ge("status","3")
                        .and(qr ->
                                qr.like("order_time",map.getOrDefault("search",""))
                                .or()
                                .like("id",map.getOrDefault("search",""))
                        );
        objectQueryWrapper.orderByDesc("order_time");
        Page<Orders> page1 = orderServices.page(page, objectQueryWrapper);
        List<Orders> records = page1.getRecords();


        for (Orders record : records) {
            QueryWrapper<orderDetail> orderDetailQueryWrapper = new QueryWrapper<>();
            orderDetailQueryWrapper.eq("order_id",record.getId());
            List<orderDetail> orderDetails = orderDetailsServices.list(orderDetailQueryWrapper);
            record.setOrderDetails(orderDetails);

            User user = userServices.getById(record.getUserId());
            user.setPassword("");
            record.setOrderuser(user);
        }

        Map<String,Object> result = new HashMap<>();
        result.put("data",records);
        result.put("total",page1.getTotal());

        return R.success(result);
    }

    @RequestMapping("/getallunfinish")
    public R getallunfinish(){
        QueryWrapper<Orders> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.lt("status",3);
        List<Orders> list = orderServices.list(objectQueryWrapper);

        for (Orders record : list) {
            QueryWrapper<orderDetail> orderDetailQueryWrapper = new QueryWrapper<>();
            orderDetailQueryWrapper.eq("order_id",record.getId());
            List<orderDetail> orderDetails = orderDetailsServices.list(orderDetailQueryWrapper);
            record.setOrderDetails(orderDetails);

            User user = userServices.getById(record.getUserId());
            user.setPassword("");
            record.setOrderuser(user);
        }
        return R.success(list);
    }

    @PostMapping("/changestatus")
    public R changestatus(@RequestBody Map<String,String> map){
        String id = map.getOrDefault("id", "");
        int status = Integer.parseInt(map.getOrDefault("status", "0"));
        Orders byId = orderServices.getById(id);
        byId.setStatus(status);
        orderServices.updateById(byId);

        String resultmsg="";
        if(status == 1){
            resultmsg = "确认成功！";
        }else if(status == 2){
            resultmsg = "备餐成功！";
        }else if(status == 3){
            resultmsg = "订单已完成！";
        }else{
            User user = userServices.getById(byId.getUserId());
            user.setRank((int) (user.getRank() - byId.getAmount()));
            userServices.updateById(user);
            resultmsg = "订单已取消！";
        }
        return R.success(resultmsg);
    }



}
