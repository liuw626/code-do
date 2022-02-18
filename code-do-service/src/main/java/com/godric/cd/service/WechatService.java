package com.godric.cd.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.godric.cd.constant.WechatConstant;
import com.godric.cd.constant.WxUrlConstant;
import com.godric.cd.exception.BizErrorEnum;
import com.godric.cd.exception.BizException;
import com.godric.cd.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class WechatService {

    @Autowired
    private WechatConstant wechatConstant;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String PARAM_GRANT_TYPE = "grant_type";

    private static final String GRANT_TYPE_VALUE = "client_credential";

    private static final String PARAM_APP_ID = "appid";

    private static final String PARAM_SECRET = "secret";

    private static final String REDIS_KEY = "wechat_token";

    public String getToken() {
        String value = redisTemplate.opsForValue().get(REDIS_KEY);
        if (StringUtils.isNotBlank(value)) {
            return value;
        }
        Map<String, String> params = new HashMap<>();
        params.put(PARAM_GRANT_TYPE, GRANT_TYPE_VALUE);
        params.put(PARAM_APP_ID, wechatConstant.getAppId());
        params.put(PARAM_SECRET, wechatConstant.getAppSecret());

        String res = HttpUtil.doGet(WxUrlConstant.GET_ACCESS_TOKEN, params);
        if (StringUtils.isBlank(res)) {
            throw new BizException(BizErrorEnum.WECHAT_SERVICE_ERROR);
        }
        JSONObject resJson = JSON.parseObject(res);
        Object tokenObj = resJson.get("access_token");
        Object expireTimeObj = resJson.get("expires_in");
        if (tokenObj == null || expireTimeObj == null) {
            throw new BizException(BizErrorEnum.WECHAT_SERVICE_ERROR);
        }
        String token = tokenObj.toString();
        Integer expireTime = (Integer) expireTimeObj;
        redisTemplate.opsForValue().set(REDIS_KEY, token, expireTime, TimeUnit.SECONDS);
        return token;
    }

}
