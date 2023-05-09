package com.wantoper.XiaoJi.Services.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wantoper.XiaoJi.Bean.ShopTable;
import com.wantoper.XiaoJi.Mapper.ShopTableMapper;
import com.wantoper.XiaoJi.Services.TableServices;
import org.springframework.stereotype.Service;

@Service
public class ShopTableImpl extends ServiceImpl<ShopTableMapper, ShopTable> implements TableServices {
}
