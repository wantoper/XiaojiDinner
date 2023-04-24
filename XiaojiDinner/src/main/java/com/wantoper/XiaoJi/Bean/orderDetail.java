package com.wantoper.XiaoJi.Bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class orderDetail {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String orderId;
    private String dishId;
    private int count;
    private String name;
    private String image;
    private String description;
    private float price;
}
