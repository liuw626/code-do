package com.godric.cd.controller;

import com.alibaba.fastjson.JSON;
import com.godric.cd.exception.BizErrorEnum;
import com.godric.cd.exception.BizException;
import com.godric.cd.result.BaseResult;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("wechat")
public class WechatController {

    @Resource
    private WxMpService mpService;

    @Resource
    private WxMpMessageRouter router;

    @PostMapping("/handler/message")
    public BaseResult receiveMessage(HttpServletRequest request, HttpServletResponse response)
            throws IOException, WxErrorException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        // 判断是否为公众平台发的消息
        String source = request.getHeader("x-wx-source");
        if (StringUtils.isBlank(source)) {
            throw new BizException(BizErrorEnum.NOT_MP_MESSAGE);
        }

        // 加密类型
        String encryptType = StringUtils.isBlank(request.getParameter("encrypt_type")) ? "raw"
                : request.getParameter("encrypt_type");

        WxMpXmlMessage inMessage;
        if ("aes".equals(encryptType)) {
            // aes加密消息
            String signature = request.getParameter("signature");
            String nonce = request.getParameter("nonce");
            String timestamp = request.getParameter("timestamp");
            log.info("receiveMessage: signature:{}, nonce:{}, timestamp:{}", signature, nonce, timestamp);
            if (!mpService.checkSignature(timestamp, nonce, signature)) {
                throw new BizException(BizErrorEnum.INVALID_MP_REQUEST);
            }
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
        log.info("toXml, res:{}", res);
        response.reset();
        log.info("before write res:{}, response:{}", res, JSON.toJSONString(response));
        response.getWriter().write(res);
        log.info("after write res:{}, response:{}", res, JSON.toJSONString(response));
        return BaseResult.success();
    }

}
