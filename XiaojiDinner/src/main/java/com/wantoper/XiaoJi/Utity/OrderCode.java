package com.wantoper.XiaoJi.Utity;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class OrderCode {
    private static Integer code = 0;
    private static LocalDate lastupdatedate = LocalDate.now();

    public static String getcode(){
        LocalDate now = LocalDate.now();
        if(now.isAfter(lastupdatedate)){
            code = 0;
            lastupdatedate = now;
        }
        return String.format("%04d", ++code);
    }
}
