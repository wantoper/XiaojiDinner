package com.wantoper.XiaoJi.Bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String name;
    private String phone;
    private String sex;
    private String avatar;
    private int status;
    private String password;

    private int rank;
    private int orderNumber;
}
