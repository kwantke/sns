package com.project.sns.controller;

import com.project.sns.controller.request.UserJoinRequest;
import com.project.sns.controller.request.UserLoginRequest;
import com.project.sns.controller.response.Response;
import com.project.sns.controller.response.UserJoinResponse;
import com.project.sns.controller.response.UserLoginResponse;
import com.project.sns.model.User;
import com.project.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;


  // TODO : implement
  @PostMapping("/join")
  public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
    User user = userService.join(request.getUserName(), request.getPassword());
    return Response.success(UserJoinResponse.fromUser(user));
  }

  @PostMapping("/login")
  public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
    String token = userService.login(request.getUserName(), request.getPassword());
    return Response.success(new UserLoginResponse(token));
  }
}
