package com.project.sns.controller;

import com.project.sns.controller.request.PostCreateRequest;
import com.project.sns.controller.response.Response;
import com.project.sns.model.User;
import com.project.sns.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;
  @PostMapping
  public Response<Void> create(@RequestBody PostCreateRequest request, Authentication authentication) {
    //User user = (User) authentication.getPrincipal();
    postService.create(request.getTitle(), request.getBody(), authentication.getName());

    return Response.success(null);
  }
}
