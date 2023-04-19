package com.wantoper.XiaoJi.Controller.Admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wantoper.XiaoJi.Bean.Dish;
import com.wantoper.XiaoJi.Bean.R;
import com.wantoper.XiaoJi.Controller.Pulic.PublicController;
import com.wantoper.XiaoJi.Services.DishServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/dish")
public class DishController {

    @Autowired
    private DishServices dishServices;

    @RequestMapping("/getall")
    public R getall(@RequestBody Map<String,String> map){
        System.out.println(map.get("index"));
        System.out.println(map.get("size"));
        System.out.println(map.get("name"));

        Page<Dish> page=new Page<Dish>(Integer.parseInt(map.get("index")),Integer.parseInt(map.get("size")));

        QueryWrapper<Dish> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.like("name",map.getOrDefault("name",""));
        Page<Dish> pages = dishServices.page(page, objectQueryWrapper);


        Map<String,Object> result = new HashMap<>();
        result.put("data",pages.getRecords());
        result.put("total",pages.getTotal());
        return R.success(result);
    }

    @RequestMapping("/add")
    public R add(@RequestBody Dish dish){
        try {
            dishServices.save(dish);
        }catch (Exception e){
            return R.error("添加失败！");
        }
        return R.success("添加成功！");
    }

    @RequestMapping("/edit")
    public R edit(@RequestBody Dish dish){
        boolean b = dishServices.updateById(dish);
        return b ? R.success("修改成功！") : R.error("修改失败！");
    }

    @RequestMapping("/del")
    public R del(@RequestBody Map<String,String> map){
        boolean b = dishServices.removeById(map.getOrDefault("id",""));
        return b ? R.success("删除成功！") : R.error("删除失败！");
    }
}
