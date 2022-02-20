package com.godric.cd.handler;

import com.alibaba.fastjson.JSON;
import com.godric.cd.constant.RedisConstant;
import com.godric.cd.constant.TimeConstant;
import com.godric.cd.dto.WechatIdDTO;
import com.godric.cd.repository.CacheRepository;
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
    private CacheRepository cacheRepository;

    private static final String VERIFY_CODE_CONTENT = "验证码";

    private static final String DEFAULT_REPLY = "对不起, 还不明白您的问题哦~";

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        String openId = wxMpXmlMessage.getOpenId();
        String unionId = wxMpXmlMessage.getUnionId();
        String content = wxMpXmlMessage.getContent();

        log.info("handler openId:{}, unionId:{}, content:{}", openId, unionId, content);

        String res = DEFAULT_REPLY;

        if (VERIFY_CODE_CONTENT.equals(content)) {
            String verifyCode = userService.generateVerifyCode();
            res = String.format("验证码: %s, 10分钟内有效", verifyCode);
            String key = String.format(RedisConstant.VERIFY_CODE, verifyCode);
            WechatIdDTO dto = new WechatIdDTO(openId, unionId);
            cacheRepository.set(key, JSON.toJSONString(dto), TimeConstant.TEN_MINUTE);
        }

        return WxMpXmlOutMessage.TEXT().content(res).fromUser(wxMpXmlMessage.getToUser()).toUser(wxMpXmlMessage.getFromUser()).build();
    }

}
