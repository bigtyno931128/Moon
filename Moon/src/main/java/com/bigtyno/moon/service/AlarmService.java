package com.bigtyno.moon.service;

import com.bigtyno.moon.exception.ErrorCode;
import com.bigtyno.moon.exception.MoonApplicationException;
import com.bigtyno.moon.model.AlarmArgs;
import com.bigtyno.moon.model.AlarmType;
import com.bigtyno.moon.model.entity.AlarmEntity;
import com.bigtyno.moon.model.entity.UserEntity;
import com.bigtyno.moon.repository.AlarmRepository;
import com.bigtyno.moon.repository.EmitterRepository;
import com.bigtyno.moon.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmService {

    private final static Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    // 화면단에서 alarm 이라는 이름으로 이벤트를 발생시켜야 구독
    private final static String ALARM_NAME = "alarm";
    private final EmitterRepository emitterRepository;
    private final AlarmRepository alarmRepository;
    private final UserEntityRepository userEntityRepository;

    public void send(AlarmType type, AlarmArgs args , Long receivedUserId ) {

        UserEntity user = userEntityRepository.findById(receivedUserId).orElseThrow(()->
                new MoonApplicationException(ErrorCode.USER_NOT_FOUND));
        //alarm save
        AlarmEntity alarmEntity = alarmRepository.save(AlarmEntity.of(user,type,args));

        emitterRepository.get(receivedUserId).ifPresentOrElse(sseEmitter -> {
            try {
                sseEmitter.send(SseEmitter.event().id(alarmEntity.getId().toString()).name(ALARM_NAME).data("새로운 알람"));
            }catch (IOException exception){
                emitterRepository.delete(receivedUserId);
                throw new MoonApplicationException(ErrorCode.ALARM_CONNECT_ERROR);
            }
        }, ()-> log.info("No emitter founded"));
    }

    public SseEmitter connectAlarm(Long userId) {
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

        emitterRepository.save(userId, sseEmitter);

        sseEmitter.onCompletion(()-> emitterRepository.delete(userId));
        sseEmitter.onTimeout(()-> emitterRepository.delete(userId));

        try {
            sseEmitter.send(SseEmitter.event().id("").name(ALARM_NAME).data("연결되었습니다."));
        }catch (IOException exception) {
            throw new MoonApplicationException(ErrorCode.ALARM_CONNECT_ERROR);
        }
        return sseEmitter;
    }
}
