package com.godric.cd.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CodeDoConstant {

    @Value("${wx.mp.appId}")
    public static final String WX_APP_ID = "wxf6d85b99a6b0f5c9";

    public static final String WX_APP_SECRET = "0b52c6a32f0df3be65ad709dbcb2b209";

}
