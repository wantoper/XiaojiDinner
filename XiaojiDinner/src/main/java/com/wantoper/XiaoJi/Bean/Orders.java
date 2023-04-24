package com.wantoper.XiaoJi.Bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@Data
public class Orders {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private int status;
    private String userId;


    @TableField(fill = FieldFill.INSERT,value = "order_time")
    private String createTime;
    private float amount;
    private String remark;
    private int tableNo;
    private String code;
}
