package com.godric.cd.controller;

import com.alibaba.fastjson.JSON;
import com.godric.cd.exception.BizErrorEnum;
import com.godric.cd.exception.BizException;
import com.godric.cd.result.BaseResult;
import com.godric.cd.result.DataResult;
import com.godric.cd.service.WechatService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("wechat")
public class WechatController {

    @Autowired
    private WxMpService mpService;

    @Autowired
    private WxMpMessageRouter router;

    @Autowired
    private WechatService wechatService;

    @GetMapping("token/get")
    public DataResult<String> getToken() {
        return DataResult.success(wechatService.getToken());
    }

    @GetMapping("handler/message")
    public String checkMessage(String signature, String timestamp, String nonce, String echostr) {
        if (mpService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }
        throw new BizException(BizErrorEnum.NOT_MP_MESSAGE);
    }

    @PostMapping(value = "/handler/message", produces = "application/text;charset=utf-8")
    public String receiveMessage(HttpServletRequest request, HttpServletResponse response)
            throws IOException, WxErrorException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String signature = request.getParameter("signature");
        String nonce = request.getParameter("nonce");
        String timestamp = request.getParameter("timestamp");
        log.info("receiveMessage: signature:{}, nonce:{}, timestamp:{}", signature, nonce, timestamp);
        if (!mpService.checkSignature(timestamp, nonce, signature)) {
            throw new BizException(BizErrorEnum.INVALID_MP_REQUEST);
        }

        // 加密类型
        String encryptType = StringUtils.isBlank(request.getParameter("encrypt_type")) ? "raw"
                : request.getParameter("encrypt_type");

        WxMpXmlMessage inMessage;
        if ("aes".equals(encryptType)) {
            // aes加密消息
            String msgSignature = request.getParameter("msg_signature");

            // 解密消息
            inMessage = WxMpXmlMessage
                    .fromEncryptedXml(request.getInputStream(), mpService.getWxMpConfigStorage(), timestamp,
                            nonce, msgSignature);

        } else if ("raw".equals(encryptType)) {
            inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
        } else {
            throw new BizException(BizErrorEnum.UNKNOWN_ENCRYPT_TYPE);
        }
        log.info("message content = {}", inMessage.getContent());

        // 路由消息并处理
        WxMpXmlOutMessage outMessage = router.route(inMessage);
        log.info("outMessage:{}", JSON.toJSONString(outMessage));
        if (Objects.isNull(outMessage)) {
            throw new BizException(BizErrorEnum.INVALID_ROUTER);
        }

        String res = "aes".equals(encryptType) ? outMessage.toEncryptedXml(mpService.getWxMpConfigStorage()) : outMessage.toXml();
        log.info("res:{}", res);
        return res;
    }

}
