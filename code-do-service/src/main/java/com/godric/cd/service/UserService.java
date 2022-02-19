package com.godric.cd.service;

import com.alibaba.fastjson.JSON;
import com.godric.cd.constant.RedisConstant;
import com.godric.cd.dto.WechatIdDTO;
import com.godric.cd.exception.BizErrorEnum;
import com.godric.cd.exception.BizException;
import com.godric.cd.po.UserPO;
import com.godric.cd.repository.CacheRepository;
import com.godric.cd.repository.UserRepository;
import com.godric.cd.utils.CodeUtil;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CacheRepository cacheRepository;

    public int insert(String username, String avatar, String openId, String unionId) {
        UserPO user = UserPO.builder().username(username).avatar(avatar).openId(openId).unionId(unionId).build();
        return userRepository.insert(user);
    }

    public UserPO fillCode(String code) {
        String key = String.format(RedisConstant.VERIFY_CODE, generateVerifyCode());
        String value = cacheRepository.get(key);
        if (StringUtils.isBlank(value)) {
            throw new BizException(BizErrorEnum.INVALID_VERIFY_CODE);
        }
        WechatIdDTO dto = JSON.parseObject(value, WechatIdDTO.class);
        UserPO userPO = userRepository.queryByOpenIdAndUnionId(dto.getOpenId(), dto.getUnionId());
        if (Objects.isNull(userPO)) {
            // todo query from wechat and insert
        }

        return userPO;
    }

    public String generateVerifyCode() {
        String verifyCode = "";
        String key = "";
        do {
            verifyCode = CodeUtil.randomCode();
            key = String.format(RedisConstant.VERIFY_CODE, verifyCode);
        } while (StringUtils.isNotBlank(cacheRepository.get(key)));
        return verifyCode;
    }

}
