package com.godric.cd.controller;

import com.godric.cd.exception.BizErrorEnum;
import com.godric.cd.result.BaseResult;
import com.godric.cd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("insert")
    public BaseResult insert(String username, String avatar) {
        int res = userService.insert(username, avatar);
        if (res > 0) {
            return BaseResult.success();
        }
        return BaseResult.fail(BizErrorEnum.OPERATION_FAILED);
    }

}
