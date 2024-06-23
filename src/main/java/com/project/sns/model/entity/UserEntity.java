package com.project.sns.model.entity;


import com.project.sns.model.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "\"user\"") // postgresql 에 user 테이블이 기존에 존재하기
@SQLDelete(sql = "update \"user\" set deleted_at = new() where id=? ")
@Where(clause = "deleted_at is null")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "user_name")
  private String userName;

  @Column(name = "password")
  private String password;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private UserRole role = UserRole.USER;
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

  public static UserEntity of(String userName, String password){
    UserEntity userEntity = new UserEntity();
    userEntity.setUserName(userName);
    userEntity.setPassword(password);
    return userEntity;
  }
}
