package com.wantoper.XiaoJi.Handle;

import com.alibaba.fastjson.JSON;
import com.wantoper.XiaoJi.Bean.R;
import com.wantoper.XiaoJi.Config.JWTConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Component
public class UserHandle implements HandlerInterceptor {
    @Autowired
    private JWTConfig jwtConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("token");
        if (authorization == null || jwtConfig.getTokenClaim(authorization)==null){
            response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(R.userloginout("请先登录！")));
            return false;
        }
        return true;
    }
}
