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
public class LoginHandler implements HandlerInterceptor {
    @Autowired
    private JWTConfig jwtConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || jwtConfig.getTokenClaim(authorization)==null){
            response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
            Cookie[] cookies = request.getCookies();
            if (null != cookies) {
                for (int i = 0; i < cookies.length; i++) {
                    if (cookies[i].getName().equals("Authorization")){
                        cookies[i].setPath("/");
                        cookies[i].setMaxAge(0);
                        response.addCookie(cookies[i]);
                        break;
                    }
                }
            }
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(R.success("请先登录！")));
            return false;
        }
        return true;
    }
}
