package com.godric.cd.repository;

import com.godric.cd.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
public class CacheRepository {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void set(String key, String value, Integer seconds) {
        redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void test() {
        this.set(RedisConstant.REDIS_TEST_KEY, "Redis OK!", 10);
        log.info(this.get(RedisConstant.REDIS_TEST_KEY));
    }

}
