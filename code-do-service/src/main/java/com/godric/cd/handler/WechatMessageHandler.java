package com.godric.cd.handler;

import com.alibaba.fastjson.JSON;
import com.godric.cd.constant.RedisConstant;
import com.godric.cd.constant.TimeConstant;
import com.godric.cd.repository.CacheRepository;
import com.godric.cd.repository.UserRepository;
import com.godric.cd.service.UserService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class WechatMessageHandler implements WxMpMessageHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CacheRepository cacheRepository;

    private static final String VERIFY_CODE_CONTENT = "验证码";

    private static final String DEFAULT_REPLY = "对不起, 还不明白您的问题哦~";

    private static final Integer COUNT_LIMIT = 3;

    private static final Integer TIME_LIMIT = TimeConstant.MINUTE * 5;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {

        String openId = wxMpXmlMessage.getFromUser();
        String content = wxMpXmlMessage.getContent();

        log.info("handler openId:{}, content:{}", openId, content);

        String res = DEFAULT_REPLY;

        if (VERIFY_CODE_CONTENT.equals(content)) {
            // todo 判断用户是否关注, 未关注让他先关注(暂时做不了, 后续看是否有需要)
            if (!checkFrequency(openId)) {
                res = "你太快了, 歇会再试试吧";
            } else {
                String verifyCode = userService.generateVerifyCode();
                res = String.format("验证码: %s, 10分钟内有效", verifyCode);
                String key = String.format(RedisConstant.VERIFY_CODE, verifyCode);
                cacheRepository.set(key, openId, TimeConstant.TEN_MINUTE);
            }
        }

        return WxMpXmlOutMessage.TEXT().content(res).fromUser(wxMpXmlMessage.getToUser()).toUser(wxMpXmlMessage.getFromUser()).build();
    }

    private boolean checkFrequency(String openId) {
        return cacheRepository.increment(String.format(RedisConstant.CODE_FREQUENCY, openId), TIME_LIMIT) <= COUNT_LIMIT;
    }

}
