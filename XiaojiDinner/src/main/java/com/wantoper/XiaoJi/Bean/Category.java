package com.wantoper.XiaoJi.Bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Category {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String name;
    private Integer sort;
}
