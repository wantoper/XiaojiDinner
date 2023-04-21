package com.wantoper.XiaoJi.Bean;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R<T> {
    private Integer code;
    private String msg;
    private T data;
    private Map map = new HashMap(); //动态数据

    public static <T> R<T> success(T object){
        R<T> r = new R<T>();
        r.data=object;
        r.code=1;
        return r;
    }

    public static <T> R<T> error(String msg){
        R r = new R();
        r.msg=msg;
        r.code=0;
        return r;
    }

    public static <T> R<T> loginout(String msg){
        R r = new R();
        r.msg=msg;
        r.code=21;
        return r;
    }

    public static <T> R<T> userloginout(String msg){
        R r = new R();
        r.msg=msg;
        r.code=22;
        return r;
    }

    public static <T> R<T> success(T object,String msg){
        R<T> r = new R<T>();
        r.data=object;
        r.code=1;
        r.msg=msg;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
