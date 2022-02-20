package com.godric.cd.controller;

import com.godric.cd.exception.BizErrorEnum;
import com.godric.cd.po.UserPO;
import com.godric.cd.repository.UserRepository;
import com.godric.cd.result.BaseResult;
import com.godric.cd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("insert")
    public BaseResult insert(String username, String avatar, String openId) {
        UserPO user = userRepository.insert(username, avatar, openId);
        if (Objects.nonNull(user)) {
            return BaseResult.success();
        }
        return BaseResult.fail(BizErrorEnum.OPERATION_FAILED);
    }

}
