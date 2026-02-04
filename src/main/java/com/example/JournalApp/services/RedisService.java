package com.example.JournalApp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T getWeatherFromRedis(String key, Class<T> entityClass) {
        try {
            Object o = redisTemplate.opsForValue().get(key);
            ObjectMapper mapper = new ObjectMapper();
            if(o!=null){
                return mapper.readValue(o.toString(), entityClass);
            } else return null;
        } catch(Exception e) {
            log.error("Error retrieving data from Redis for key {}: {}", key, e.getMessage());
            return null;
        }
    }

    public void set(String key, Object o, Long ttl) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key,jsonString,ttl, TimeUnit.SECONDS);
        } catch(Exception e) {
            log.error("Error {}", e.getMessage());
        }
    }

}
