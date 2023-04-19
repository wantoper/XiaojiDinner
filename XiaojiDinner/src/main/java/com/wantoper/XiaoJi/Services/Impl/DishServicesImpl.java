package com.wantoper.XiaoJi.Services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wantoper.XiaoJi.Bean.Dish;
import com.wantoper.XiaoJi.Mapper.DishMapper;
import com.wantoper.XiaoJi.Services.DishServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServicesImpl extends ServiceImpl<DishMapper, Dish> implements DishServices {
}
