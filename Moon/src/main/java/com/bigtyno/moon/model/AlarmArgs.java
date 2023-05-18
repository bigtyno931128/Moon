package com.bigtyno.moon.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmArgs {
    // 알람을 발생시킨 사람 의 USER ID
    private Long fromUserId;
    private Long targetId;
}
