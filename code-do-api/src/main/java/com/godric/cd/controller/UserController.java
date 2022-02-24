package com.godric.cd.controller;

import com.godric.cd.request.CollectRequest;
import com.godric.cd.request.ThumbRequest;
import com.godric.cd.request.UserUpdateRequest;
import com.godric.cd.result.BaseResult;
import com.godric.cd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("update")
    public BaseResult update(UserUpdateRequest request) {
        userService.update(request);
        return BaseResult.success();
    }

    @PostMapping("thumb")
    public BaseResult thumb(ThumbRequest request) {
        userService.thumb(request.getTargetType(), request.getTargetId());
        return BaseResult.success();
    }

    @PostMapping("collect")
    public BaseResult collect(CollectRequest request) {
        userService.collect(request.getArticleId());
        return BaseResult.success();
    }

}
