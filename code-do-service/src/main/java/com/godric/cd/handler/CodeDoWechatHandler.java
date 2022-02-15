package com.godric.cd.handler;

import com.alibaba.fastjson.JSON;
import com.godric.cd.constant.WxUrlConstant;
import com.godric.cd.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
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

        Map<String, Object> param = new HashMap<>();
        param.put("touser", wxMpXmlMessage.getFromUser());
        Text t = new Text();
        t.content = res;
        param.put("msgtype", "text");
        param.put("text", t);

        HttpUtil.doPost(WxUrlConstant.SEND_MESSAGE, param);

        return WxMpXmlOutMessage.TEXT().content(res).fromUser(wxMpXmlMessage.getToUser()).toUser(wxMpXmlMessage.getFromUser()).build();
    }


    private static class Text {
        String content;
    }

}
