package com.project.sns.controller.response;

import com.project.sns.model.User;
import com.project.sns.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {

  private Integer id;
  private String userName;
  private UserRole userRole;

  public static UserResponse fromUser(User user) {
    return new UserResponse(user.getId(),
            user.getUsername(),
            user.getUserRole());
  }
}
