package com.godric.cd.service;

import com.godric.cd.po.UserPO;
import com.godric.cd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public int insert(String username, String avatar, String openId) {
        UserPO user = UserPO.builder().username(username).avatar(avatar).openId(openId).build();
        return userRepository.insert(user);
    }

    public void test() {
        redisTemplate.opsForValue().set("a", "aaaaaaaaaaaaa");

        System.out.println(redisTemplate.opsForValue().get("a"));
    }

}
