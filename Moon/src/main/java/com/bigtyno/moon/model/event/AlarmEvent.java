package com.bigtyno.moon.model.event;

import com.bigtyno.moon.model.AlarmArgs;
import com.bigtyno.moon.model.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmEvent {

    private Integer receivedUserId;
    private AlarmType alarmType;
    private AlarmArgs alarmArgs;

}
