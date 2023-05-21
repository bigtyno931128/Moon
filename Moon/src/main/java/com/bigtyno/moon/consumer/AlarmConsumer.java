package com.bigtyno.moon.consumer;

import com.bigtyno.moon.model.event.AlarmEvent;
import com.bigtyno.moon.service.AlarmService;
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
    public void consumeAlarm(AlarmEvent event, Acknowledgment ack) {
        log.info("Consume the event {}" , event );
        alarmService.send(event.getAlarmType(), event.getAlarmArgs(), Long.valueOf(event.getReceivedUserId()));
        ack.acknowledge();
    }
}
