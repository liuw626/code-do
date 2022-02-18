package com.godric.cd;

import com.godric.cd.constant.WechatConstant;
import com.godric.cd.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.godric.cd.dao")
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        UserService userService = ctx.getBean(UserService.class);
        userService.test();

        WechatConstant bean = ctx.getBean(WechatConstant.class);


        System.out.println("appId:" + bean.getAppId());
        System.out.println("secret:" + bean.getAppSecret());
    }

}
