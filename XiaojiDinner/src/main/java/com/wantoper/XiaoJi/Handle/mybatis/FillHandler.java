package com.wantoper.XiaoJi.Handle.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FillHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

        SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(new Date());
        this.strictInsertFill(metaObject,"createTime", String.class,formattedDate);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
