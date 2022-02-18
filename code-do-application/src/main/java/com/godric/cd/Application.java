package com.godric.cd;

import com.godric.cd.constant.WechatConstant;
import com.godric.cd.repository.CacheRepository;
import com.godric.cd.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
@MapperScan("com.godric.cd.dao")
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        CacheRepository cacheRepository = ctx.getBean(CacheRepository.class);
        cacheRepository.test();

        WechatConstant bean = ctx.getBean(WechatConstant.class);
        log.info("appId:{}, secret:{}", bean.getAppId(), bean.getAppSecret());
    }

}
