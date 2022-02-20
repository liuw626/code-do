package com.godric.cd.controller;

import com.godric.cd.exception.BizErrorEnum;
import com.godric.cd.exception.BizException;
import com.godric.cd.po.UserPO;
import com.godric.cd.result.BaseResult;
import com.godric.cd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("fillCode")
    public BaseResult fillCode(String verifyCode) {
        UserPO user = userService.fillCode(verifyCode);
        // todo login interceptor

        return BaseResult.success();
    }

    @GetMapping(value = "error/user")
    public BaseResult userErrorGet() {
        throw new BizException(BizErrorEnum.NEED_LOGIN);
    }
    @PostMapping(value = "error/user")
    public BaseResult userErrorPost() {
        throw new BizException(BizErrorEnum.NEED_LOGIN);
    }

}
