package com.project.sns.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class EmitterRepository {

  private Map<String, SseEmitter> emitterMap = new HashMap<>();

  public SseEmitter save(Integer userId, SseEmitter sseEmitter) {
    final String key = getKey(userId);
    emitterMap.put(key, sseEmitter);
    log.info("Set sseEmitter {}", userId);

    return sseEmitter;
  }

  public Optional<SseEmitter> get(Integer userid) {
    final String key = getKey(userid);
    log.info("Get sseEmitter {}", userid);
    return Optional.ofNullable(emitterMap.get(key));
  }

  public void delete(Integer userId) {
    emitterMap.remove(getKey(userId));
  }

  private String getKey(Integer userId) {
    return "EmitterL:UID: " + userId;
  }


}
