package com.wantoper.XiaoJi.Bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
public class Orders implements Comparable<Orders> {
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

    @TableField(exist = false)
    private List<orderDetail> orderDetails;

    @TableField(exist = false)
    private User orderuser;

    @SneakyThrows
    @Override
    public int compareTo(Orders o) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return  simpleDateFormat.parse(this.getCreateTime()).compareTo(simpleDateFormat.parse(o.getCreateTime()));
    }

}
