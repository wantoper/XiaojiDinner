package com.wantoper.XiaoJi.Bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.context.annotation.Bean;

@Data
public class Orders {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private int status;
    private String userId;
    private String orderTime;
    private float amount;
    private String remake;
    private int tableNo;
}
