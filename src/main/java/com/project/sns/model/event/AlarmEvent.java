package com.project.sns.model.event;

import com.project.sns.model.AlarmArgs;
import com.project.sns.model.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//UserEntity userEntity, AlarmType alarmType, AlarmArgs args
public class AlarmEvent {

  private Integer receiveUserId;
  private AlarmType alarmType;
  private AlarmArgs args;
}
