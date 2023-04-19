package com.wantoper.XiaoJi.Bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Dish {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String name;
    private String categoryId;
    private float price;
    private String image;
    private Integer status;
    private String description;
}
