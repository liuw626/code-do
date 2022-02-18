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

}
