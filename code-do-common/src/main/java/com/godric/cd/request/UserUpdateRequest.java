package com.godric.cd.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateRequest {

    @NotBlank(message = "请填写用户名")
    private String username;

    private String avatar;

}
