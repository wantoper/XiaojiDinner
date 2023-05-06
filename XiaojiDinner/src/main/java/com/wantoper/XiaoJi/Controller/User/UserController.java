package com.wantoper.XiaoJi.Controller.User;

import com.wantoper.XiaoJi.Bean.R;
import com.wantoper.XiaoJi.Bean.User;
import com.wantoper.XiaoJi.Config.JWTConfig;
import com.wantoper.XiaoJi.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserServices userServices;
    @Autowired
    private JWTConfig jwtConfig;

    @RequestMapping("/info")
    public R userinfo(HttpServletRequest request){
        String token = request.getHeader("token");
        String userid = jwtConfig.getTokenClaim(token).getSubject();
        User user = userServices.getById(userid);
        return R.success(user);
    }


}
