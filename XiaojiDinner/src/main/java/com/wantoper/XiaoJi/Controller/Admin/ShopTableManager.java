package com.wantoper.XiaoJi.Controller.Admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wantoper.XiaoJi.Bean.R;
import com.wantoper.XiaoJi.Bean.ShopTable;
import com.wantoper.XiaoJi.Services.TableServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/table")
public class ShopTableManager {

    @Value("${spring.application.indexurl}")
    private String indexurl;

    @Autowired
    private TableServices tableServices;

    @RequestMapping("/getall")
    public R test(){
        List<ShopTable> list = tableServices.list();
        list.forEach(t->t.setUrl(indexurl+t.getTableNo()));
        return R.success(list);
    }

    @RequestMapping("/add")
    public R add(@RequestBody ShopTable shopTable){
        System.out.println(shopTable);
        boolean save = tableServices.save(shopTable);
        return save ? R.success("添加成功！"):R.error("添加失败！");
    }

    @RequestMapping("/del")
    public R del(@RequestBody ShopTable shopTable){
        QueryWrapper<ShopTable> shopTableQueryWrapper = new QueryWrapper<>();
        shopTableQueryWrapper.eq("table_no",shopTable.getTableNo());
        boolean remove = tableServices.remove(shopTableQueryWrapper);
        return remove ? R.success("删除成功！"):R.error("删除失败！");
    }
}
