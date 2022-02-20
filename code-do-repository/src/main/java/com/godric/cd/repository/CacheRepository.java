package com.godric.cd.repository;

import com.godric.cd.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;
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

    public int increment(String key, Integer seconds) {
        String pre = this.get(key);
        if (StringUtils.isBlank(pre)) {
            this.set(key, "0", seconds);
        }
        Long value = redisTemplate.opsForValue().increment(key);
        return Objects.isNull(value) ? 0 : value.intValue();
    }

    public void test() {
        this.set(RedisConstant.REDIS_TEST_KEY, "Redis OK!", 10);
        log.info(this.get(RedisConstant.REDIS_TEST_KEY));
    }

}
