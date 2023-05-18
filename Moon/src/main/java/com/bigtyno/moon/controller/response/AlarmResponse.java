package com.bigtyno.moon.controller.response;

import com.bigtyno.moon.model.Alarm;
import com.bigtyno.moon.model.AlarmArgs;
import com.bigtyno.moon.model.AlarmType;
import com.bigtyno.moon.model.User;
import com.bigtyno.moon.model.entity.AlarmEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;

@Data
@Getter
@AllArgsConstructor
public class AlarmResponse {
    private Long id;
    private AlarmType alarmType;
    private AlarmArgs args;
    private String text;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp removedAt;

    public static AlarmResponse fromAlarm(Alarm alarm) {
        return new AlarmResponse(
                alarm.getId(),
                alarm.getAlarmType(),
                alarm.getArgs(),
                alarm.getAlarmType().getAlarmText(),
                alarm.getCreatedAt(),
                alarm.getUpdatedAt(),
                alarm.getRemovedAt()
        );
    }
}
