package com.wantoper.XiaoJi.Services.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wantoper.XiaoJi.Bean.Category;
import com.wantoper.XiaoJi.Mapper.CategoryMapper;
import com.wantoper.XiaoJi.Services.CategoryServices;
import org.springframework.stereotype.Service;

@Service
public class CategoryServicesImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryServices {
}
