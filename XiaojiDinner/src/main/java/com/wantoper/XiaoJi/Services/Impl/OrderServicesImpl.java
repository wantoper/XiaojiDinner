package com.wantoper.XiaoJi.Services.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wantoper.XiaoJi.Bean.Orders;
import com.wantoper.XiaoJi.Mapper.OrdersMapper;
import com.wantoper.XiaoJi.Services.OrderServices;
import org.springframework.stereotype.Service;

@Service
public class OrderServicesImpl extends ServiceImpl<OrdersMapper, Orders>  implements OrderServices  {
}
