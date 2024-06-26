package com.project.sns.model;

import com.project.sns.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
// TODO : implement
@AllArgsConstructor
public class User {

  private Integer id;
  private String userName;
  private String password;
  private UserRole userRole;
  private Timestamp registeredAt;
  private Timestamp udpatedAt;
  private Timestamp deletedAt;



  public static User fromEntity(UserEntity entity) {
    return new User(
            entity.getId(),
            entity.getUserName(),
            entity.getPassword(),
            entity.getRole(),
            entity.getRegisteredAt(),
            entity.getUpdatedAt(),
            entity.getDeletedAt()
    );
  }
}
