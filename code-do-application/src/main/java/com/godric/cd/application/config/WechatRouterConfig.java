package com.godric.cd.application.config;

import com.godric.cd.handler.WechatMessageHandler;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WechatRouterConfig {

    @Autowired
    private WxMpService wxMpService;

//    @Autowired
//    private LoginHandler loginHandler;

    @Autowired
    private WechatMessageHandler messageHandler;

//    @Autowired
//    private SubscribeHandler subscribeHandler;

    @Bean
    public WxMpMessageRouter getWxMsgRouter() {
        WxMpMessageRouter router = new WxMpMessageRouter(wxMpService);
//        // 登录
//        router.rule()
//                .async(false)
//                .msgType(XmlMsgType.TEXT)
//                .rContent("登录|登陆")
//                .handler(loginHandler)
//                .end();
        // 收到消息
        router.rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.TEXT)
                .handler(messageHandler)
                .end();
//        // 关注
//        router.rule()
//                .async(false)
//                .msgType(XmlMsgType.EVENT)
//                .event(EventType.SUBSCRIBE)
//                .handler(subscribeHandler)
//                .end();
//        // 点击按钮
//        router.rule()
//                .async(false)
//                .msgType(XmlMsgType.EVENT)
//                .event(EventType.CLICK)
//                .eventKey(CommonConstant.LOGIN_MENU_KEY)
//                .handler(loginHandler)
//                .end();
        return router;
    }

}
