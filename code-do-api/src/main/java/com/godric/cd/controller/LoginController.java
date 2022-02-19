package com.godric.cd.controller;

import com.godric.cd.po.UserPO;
import com.godric.cd.repository.UserRepository;
import com.godric.cd.result.BaseResult;
import com.godric.cd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("fillCode")
    public BaseResult fillCode(String verifyCode) {
        UserPO user = userService.fillCode(verifyCode);
        // todo login interceptor

        return BaseResult.success();
    }

}
