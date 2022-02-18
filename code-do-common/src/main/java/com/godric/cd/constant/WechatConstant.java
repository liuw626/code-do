package com.godric.cd.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class WechatConstant {

    @Value("${wx.mp.appId}")
    private String appId;

    @Value("${wx.mp.secret}")
    private String appSecret;

    public static class Url {
        /**
         * 获取token接口
         */
        public static final String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";



    }

}
