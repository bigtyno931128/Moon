package com.bigtyno.moon.model;


import com.bigtyno.moon.model.entity.AlarmEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Alarm {
    private Long id;
    private AlarmType alarmType;
    private AlarmArgs args;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp removedAt;

    public static Alarm fromEntity(AlarmEntity entity) {
        return new Alarm(
                entity.getId(),
                entity.getAlarmType(),
                entity.getArgs(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getRemovedAt()
        );
    }
}
