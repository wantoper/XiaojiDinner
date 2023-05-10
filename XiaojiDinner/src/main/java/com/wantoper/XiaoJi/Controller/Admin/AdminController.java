package com.wantoper.XiaoJi.Controller.Admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wantoper.XiaoJi.Bean.Orders;
import com.wantoper.XiaoJi.Bean.R;
import com.wantoper.XiaoJi.Bean.ShopInfo;
import com.wantoper.XiaoJi.Mapper.ShopInfoMapper;
import com.wantoper.XiaoJi.Services.CategoryServices;
import com.wantoper.XiaoJi.Services.DishServices;
import com.wantoper.XiaoJi.Services.OrderServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private ShopInfoMapper shopInfoMapper;

    @Autowired
    private OrderServices orderServices;
    @Autowired
    private CategoryServices categoryServices;
    @Autowired
    private DishServices dishServices;
    @RequestMapping("/shop/set")
    public R shopset(@RequestBody ShopInfo shopInfo){
        shopInfoMapper.updateById(shopInfo);
        return R.success("修改成功！");
    }

    @RequestMapping("/data")
    public R data(@RequestBody Map<String,String> map) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        QueryWrapper<Orders> ordersQueryWrapper = new QueryWrapper<>();
        Date date = simpleDateFormat.parse(map.getOrDefault("date", ""));
        ordersQueryWrapper.ge("order_time", date);
        ordersQueryWrapper.eq("status","3");
        List<Orders> list = orderServices.list(ordersQueryWrapper);

        List<String> xData=new ArrayList<>();
        List<Float> yIncome = new ArrayList<>();
        List<Integer> yOrder = new ArrayList<>();
        Map<String, Object> cardData = new HashMap<>();


        list.parallelStream().map(t->{
            t.setCreateTime(t.getCreateTime().substring(0,10));
            return t;
        }).collect(Collectors.groupingBy(t->t.getCreateTime(),TreeMap::new,Collectors.toList()))
                .forEach((d,l)->{
                    xData.add(d);
                    float amountsum=0;
                    int ordersum=0;
                    for (Orders orders : l) {
                        amountsum+=orders.getAmount();
                        ordersum++;
                    }
                    yIncome.add(amountsum);
                    yOrder.add(ordersum);

                    if(d.equals(simpleDateFormat.format(new Date()))){
                        cardData.put("orderCounts",ordersum);
                        cardData.put("totalIncome",amountsum);
                    }

                });

        cardData.put("categoryCounts",categoryServices.count());
        cardData.put("commodityCounts",dishServices.count());
        Map<String, Object> ResultData = new HashMap<>();
        ResultData.put("cardData",cardData);
        ResultData.put("xData",xData);
        ResultData.put("yIncome",yIncome);
        ResultData.put("yOrder",yOrder);

        return R.success(ResultData);
    }
}
