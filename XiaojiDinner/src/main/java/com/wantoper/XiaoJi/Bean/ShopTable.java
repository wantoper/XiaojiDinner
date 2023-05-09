package com.wantoper.XiaoJi.Bean;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class ShopTable {
    String tableNo;
    @TableField(exist = false)
    String url;
}
