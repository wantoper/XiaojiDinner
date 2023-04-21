package com.wantoper.XiaoJi.Config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.wantoper.XiaoJi.Handle.AdminHandle;
import com.wantoper.XiaoJi.Handle.LoginHandler;
import com.wantoper.XiaoJi.Handle.UserHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String absolutePath = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath() + "\\src\\main\\resources\\static\\img\\";
        registry.addResourceHandler("/static/img/**").addResourceLocations("file:"+absolutePath);
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

    @Autowired
    private AdminHandle adminHandle;
    @Autowired
    private LoginHandler loginHandler;

    @Autowired
    private UserHandle userHandle;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration1 = registry.addInterceptor(loginHandler);
        interceptorRegistration1.addPathPatterns("/api/admin/**");
        interceptorRegistration1.order(0);

        InterceptorRegistration interceptorRegistration = registry.addInterceptor(adminHandle);
        interceptorRegistration.addPathPatterns("/api/admin/**");
        interceptorRegistration.order(1);

        InterceptorRegistration userinterceptorRegistration = registry.addInterceptor(userHandle);
        userinterceptorRegistration.addPathPatterns("/api/user/**");

    }
}
