package com.godric.cd.service;

import com.godric.cd.constant.WechatConstant;
import com.godric.cd.constant.WxUrlConstant;
import com.godric.cd.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WechatService {

    @Autowired
    private WechatConstant wechatConstant;

    private static final String PARAM_GRANT_TYPE = "grant_type";

    private static final String GRANT_TYPE_VALUE = "client_credential";

    private static final String PARAM_APP_ID = "appid";

    private static final String PARAM_SECRET = "secret";

    public String getToken() {
        Map<String, String> params = new HashMap<>();
        params.put(PARAM_GRANT_TYPE, GRANT_TYPE_VALUE);
        params.put(PARAM_APP_ID, wechatConstant.getAppId());
        params.put(PARAM_SECRET, wechatConstant.getAppSecret());

        return HttpUtil.doGet(WxUrlConstant.GET_ACCESS_TOKEN, params);
    }

}
