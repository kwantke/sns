package com.project.sns.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor // 변수가 1개일때는 이것을 추가해주어야 한다.
public class PostCommentRequest {

  private String comment;


}
