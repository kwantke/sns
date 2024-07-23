package com.project.sns.model.entity;

import com.project.sns.model.AlarmArgs;
import com.project.sns.model.AlarmType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class) // jsonb 는 JsonBinaryType 의 약자이다.
@Table(name = "\"alarm\"", indexes = {
        @Index(name = "user_id_idx", columnList = "user_id")
})
@SQLDelete(sql = "update \"alarm\" set deleted_at = now() where id = ?")
@Where(clause = "deleted_at is null")
public class AlarmEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  // 알림을 받은 사람
  @ManyToOne(fetch = FetchType.LAZY) // LAZY 는 user 정보가 있는징 없는지 상관없이 Alarm 테이블을 조회하여 가져온다. default는 EAGER
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @Enumerated(EnumType.STRING)
  private AlarmType alarmType;

  @Type(type = "jsonb")
  @Column(columnDefinition = "jsonb")
  private AlarmArgs args;

  @Column(name = "registered_at")
  private Timestamp registeredAt;

  @Column(name = "updated_at")
  private Timestamp updatedAt;

  @Column(name = "deleted_at")
  private Timestamp deletedAt;

  @PrePersist
  void registeredAt() {
    this.registeredAt = Timestamp.from(Instant.now());
  }

  @PreUpdate
  void updatedAt() {
    this.updatedAt = Timestamp.from(Instant.now());
  }

  public static AlarmEntity of(UserEntity userEntity, AlarmType alarmType, AlarmArgs args) {
    AlarmEntity entity = new AlarmEntity();
    entity.setUser(userEntity);
    entity.setAlarmType(alarmType);
    entity.setArgs(args);
    return entity;
  }

}
