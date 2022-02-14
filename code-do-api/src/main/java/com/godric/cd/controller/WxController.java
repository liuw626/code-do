package com.godric.cd.controller;

import com.godric.cd.constant.CodeDoConstant;
import com.godric.cd.constant.WxUrlConstant;
import com.godric.cd.result.DataResult;
import com.godric.cd.utils.HttpUtil;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("wx")
public class WxController {

    @GetMapping("access/token/get")
    public DataResult<String> getAccessToken() {
        Map<String, String> param = new HashMap<>();
        param.put("grant_type", "client_credential");
        param.put("appid", CodeDoConstant.WX_APP_ID);
        param.put("secret", CodeDoConstant.WX_APP_SECRET);

        String s = HttpUtil.doGet(WxUrlConstant.GET_ACCESS_TOKEN, param);
        return DataResult.success(s);
    }

}
