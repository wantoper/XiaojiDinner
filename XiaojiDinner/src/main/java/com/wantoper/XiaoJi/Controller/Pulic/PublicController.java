package com.wantoper.XiaoJi.Controller.Pulic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wantoper.XiaoJi.Bean.Category;
import com.wantoper.XiaoJi.Bean.Dish;
import com.wantoper.XiaoJi.Bean.R;
import com.wantoper.XiaoJi.Mapper.ShopInfoMapper;
import com.wantoper.XiaoJi.Services.CategoryServices;
import com.wantoper.XiaoJi.Services.DishServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PublicController {

    @RequestMapping("/upload")
    public R upload(MultipartFile file, HttpServletRequest request) throws IOException {
        String filename =UUID.randomUUID().toString();
        String suffix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        filename=filename+suffix;

        ApplicationHome applicationHome = new ApplicationHome(this.getClass());

        //D:\Desktop\毕设\XiaoJidinner\src\main\resources\static\img
        String absolutePath = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath() + "\\src\\main\\resources\\static\\img";

        File dir = new File(absolutePath +"\\"+ filename);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        file.transferTo(dir);

        return R.success(filename);
    }

    @RequestMapping("/download")
    public void download(String name, HttpServletResponse response){

        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String absolutePath = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath() + "\\src\\main\\resources\\static\\img\\"+name;

        try {
            FileInputStream fileInputStream = new FileInputStream(new File(absolutePath));
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len=0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            outputStream.close();
            fileInputStream.close();
        }catch (Exception e){
        }
    }

    @Autowired
    private CategoryServices categoryServices;
    @Autowired
    private DishServices dishServices;

    @RequestMapping("/menu")
    public R menu(){
        ArrayList<Map<String,Object>> resultlist = new ArrayList<>();


        List<Category> list = categoryServices.list();
        Collections.sort(list, new Comparator<Category>() {
            @Override
            public int compare(Category o1, Category o2) {
                return (o1.getSort())-o2.getSort();
            }
        });
        for (Category category : list) {
            HashMap<String, Object> result = new HashMap<>();
            QueryWrapper<Dish> dishQueryWrapper = new QueryWrapper<>();
            dishQueryWrapper.eq("category_id",category.getId());
            List<Dish> list1 = dishServices.list(dishQueryWrapper);

            result.put("name",category.getName());
            result.put("sort",category.getSort());
            result.put("foods",list1);

            resultlist.add(result);
        }

        return R.success(resultlist);
    }

    @Autowired
    private ShopInfoMapper shopInfoMapper;
    @RequestMapping("/shop/get")
    public R shopinfo(){
        return R.success(shopInfoMapper.selectById(1));
    }
}
