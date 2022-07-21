package com.godric.cd.controller;

import com.alibaba.fastjson.JSON;
import com.godric.cd.exception.BizErrorEnum;
import com.godric.cd.exception.BizException;
import com.godric.cd.po.UserPO;
import com.godric.cd.result.BaseResult;
import com.godric.cd.result.DataResult;
import com.godric.cd.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("do")
    public DataResult<String> doTest(int k) {
        if (k == 1) {
            throw new BizException(BizErrorEnum.PARAM_CANNOT_NULL, "k");
        }

        return DataResult.success("success");
    }

    @PostMapping("post")
    public BaseResult post(UserPO user) {
        log.info("TestController.post user:{}", JSON.toJSONString(user));
        return BaseResult.success();
    }

    @GetMapping("doPost")
    public BaseResult doPost() {
        String url = "http://localhost/test/post";
        Map<String, Object> param = new HashMap<>();
        param.put("username", "aaa");
        param.put("avatar", "bbb");
        param.put("openId", "1111");


        HttpUtil.doPost(url, param);
        return BaseResult.success();
    }

}
