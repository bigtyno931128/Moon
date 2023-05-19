package com.bigtyno.moon.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.*;

@Slf4j
@Repository
public class EmitterRepository {

    // local cache 사용 TODO: server instance 가 늘어남에 따라 처리방법 고민
    private Map<String, SseEmitter> emitterMap = new HashMap<>();

    public SseEmitter save(Long userId, SseEmitter sseEmitter) {
        final String key = getKey(userId);
        emitterMap.put(key, sseEmitter);
        log.info("Set sseEmitter {}", userId);
        return sseEmitter;
    }

    public Optional<SseEmitter> get(Long userId) {
        final String key = getKey(userId);
        log.info("Get sseEmitter {}", userId);
        return Optional.ofNullable(emitterMap.get(key));
    }

    public void delete(Long userId) {
        emitterMap.remove(getKey(userId));
    }

    public String getKey(Long userId) {
        return "Emitter:UID" + userId;
    }
}
