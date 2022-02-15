package com.godric.cd.controller;

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
        // 校验消息签名，判断是否为公众平台发的消息
        String source = request.getParameter("x-wx-source");

        log.info("receiveMessage：source:{}", source);

        String signature = request.getParameter("signature");
        String nonce = request.getParameter("nonce");
        String timestamp = request.getParameter("timestamp");
        log.info("receiveMessage: signature:{}, nonce:{}, timestamp:{}", signature, nonce, timestamp);
        if (!mpService.checkSignature(timestamp, nonce, signature)) {
            response.getWriter().println("非法请求");
        }
        // 加密类型
        String encryptType = StringUtils.isBlank(request.getParameter("encrypt_type")) ? "raw"
                : request.getParameter("encrypt_type");
        // 明文消息
        if ("raw".equals(encryptType)) {
            return BaseResult.success();
        }
        // aes 加密消息
        if ("aes".equals(encryptType)) {
            // 解密消息
            String msgSignature = request.getParameter("msg_signature");
            WxMpXmlMessage inMessage = WxMpXmlMessage
                    .fromEncryptedXml(request.getInputStream(), mpService.getWxMpConfigStorage(), timestamp,
                            nonce,
                            msgSignature);
            log.info("message content = {}", inMessage.getContent());
            // 路由消息并处理
            WxMpXmlOutMessage outMessage = router.route(inMessage);
            if (outMessage == null) {
                response.getWriter().write("");
            } else {
                response.getWriter().write(outMessage.toEncryptedXml(mpService.getWxMpConfigStorage()));
            }
            return BaseResult.success();
        }
        response.getWriter().println("不可识别的加密类型");
        return BaseResult.fail("不可识别的加密类型");
    }

}
