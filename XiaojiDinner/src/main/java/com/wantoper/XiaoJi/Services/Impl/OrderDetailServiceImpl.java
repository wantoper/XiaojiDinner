package com.wantoper.XiaoJi.Services.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wantoper.XiaoJi.Bean.orderDetail;
import com.wantoper.XiaoJi.Mapper.OrderDetailMapper;
import com.wantoper.XiaoJi.Services.OrderDetailsServices;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper,orderDetail> implements OrderDetailsServices {

}
