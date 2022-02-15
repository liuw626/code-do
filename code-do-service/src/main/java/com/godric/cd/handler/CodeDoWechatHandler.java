package com.godric.cd.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class CodeDoWechatHandler implements WxMpMessageHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        String openId = wxMpXmlMessage.getOpenId();
        String unionId = wxMpXmlMessage.getUnionId();
        String content = wxMpXmlMessage.getContent();

        String res = "defaultRes";

        if ("111".equals(content)) {
            res = "111111";
        }

        log.info("wxMpXmlMessage:{}", JSON.toJSONString(wxMpXmlMessage));

        return WxMpXmlOutMessage.TEXT().content(res).fromUser(wxMpXmlMessage.getToUser()).toUser(wxMpXmlMessage.getFromUser()).build();
    }

}
