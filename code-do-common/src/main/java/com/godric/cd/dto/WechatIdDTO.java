package com.godric.cd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WechatIdDTO {

    private String openId;

    private String unionId;

}
