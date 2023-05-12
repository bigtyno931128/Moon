package com.bigtyno.moon.model.entity;

import com.bigtyno.moon.model.AlarmArgs;
import com.bigtyno.moon.model.AlarmType;

import java.sql.Timestamp;

public class AlarmEntity {
    private Integer id = null;
    private UserEntity user;
    private AlarmType alarmType;
    private AlarmArgs args;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp removedAt;

}
