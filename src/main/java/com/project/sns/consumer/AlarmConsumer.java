package com.project.sns.consumer;

import com.project.sns.model.event.AlarmEvent;
import com.project.sns.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmConsumer {

  private final AlarmService alarmService;

  @KafkaListener(topics = "${spring.kafka.topic.alarm}")
  public void consumerAlarm(AlarmEvent event, Acknowledgment ack) {
    log.info("Consumer the event {}",event);
    alarmService.send(event.getAlarmType(), event.getArgs(), event.getReceiveUserId());
    ack.acknowledge(); // send()가 완료되면 ack를 날린다
  }


}
