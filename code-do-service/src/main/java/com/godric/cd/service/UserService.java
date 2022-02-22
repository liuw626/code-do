package com.godric.cd.service;

import com.godric.cd.constant.CodeDoConstant;
import com.godric.cd.constant.RedisConstant;
import com.godric.cd.exception.BizErrorEnum;
import com.godric.cd.exception.BizException;
import com.godric.cd.po.User;
import com.godric.cd.repository.CacheRepository;
import com.godric.cd.repository.UserRepository;
import com.godric.cd.utils.CodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CacheRepository cacheRepository;

    private static final String DEFAULT_USERNAME_PREFIX = "微信用户";

    public User fillCode(String code) {
        String key = String.format(RedisConstant.VERIFY_CODE, code);
        String openId = cacheRepository.get(key);
        if (StringUtils.isBlank(openId)) {
            throw new BizException(BizErrorEnum.INVALID_VERIFY_CODE);
        }
        User user = userRepository.queryByOpenId(openId);
        if (Objects.isNull(user)) {
            return userRepository.insert(generateName(), CodeDoConstant.DEFAULT_AVATAR, openId);
        }

        return user;
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

    public String generateName() {
        String suffix = UUID.randomUUID().toString().substring(0, 8);
        return DEFAULT_USERNAME_PREFIX + suffix;
    }

}
