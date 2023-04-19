package com.wantoper.XiaoJi.Controller.Admin;

import com.wantoper.XiaoJi.Bean.Category;
import com.wantoper.XiaoJi.Bean.R;
import com.wantoper.XiaoJi.Services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/category")
public class CategoryController {

    @Autowired
    private CategoryServices categoryServices;

    @RequestMapping("/getall")
    public R getall(){
        return R.success(categoryServices.list());
    }

    @RequestMapping("/add")
    public R create(@RequestBody Category c){
        try {
            categoryServices.save(c);
        }catch (Exception e){
            return R.error("添加失败！");
        }
        return R.success("添加成功！");
    }

    @RequestMapping("/del")
    public R del(@RequestBody Map<String,String> map){
        boolean id = categoryServices.removeById(map.get("_id"));
        System.out.println("del:"+map.get("_id"));
        return id?R.success("删除成功！"):R.error("删除失败！");
    }

    @RequestMapping("/edit")
    public R edit(@RequestBody Category c){
        boolean id = categoryServices.updateById(c);
        return id?R.success("修改成功！"):R.error("修改失败！");
    }
}
