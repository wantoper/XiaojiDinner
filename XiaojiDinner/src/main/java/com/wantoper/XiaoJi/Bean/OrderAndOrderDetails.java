package com.wantoper.XiaoJi.Bean;

import lombok.Data;

import java.util.List;

@Data
public class OrderAndOrderDetails {
    private int deskNo;
    private String remark;
    private float totalPrice;
    private List<Dish> order;
}
