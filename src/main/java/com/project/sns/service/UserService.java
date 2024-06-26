package com.project.sns.service;

import com.project.sns.exception.ErrorCode;
import com.project.sns.exception.SnsApplicationException;
import com.project.sns.model.User;
import com.project.sns.model.entity.UserEntity;
import com.project.sns.repository.UserEntityRepository;
import com.project.sns.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserEntityRepository userEntityRepository;
  private final BCryptPasswordEncoder encoder;

  @Value("${jwt.secret-key}")
  private String secretKey ="";

  @Value("${jwt.token.expired-time-ms}")
  private Long expiredTimeMs = 0L;

  // TODO : implement
  public User join(String userName, String password) {
    //회원가입하려는 userName으로 회원가입된 user가 있는지
    userEntityRepository.findByUserName(userName).ifPresent(it->{
      throw new SnsApplicationException(ErrorCode.DUPULICATED_USER_NAME, String.format("%s is dulplicated", userName));
    });

    // 회원가입 진행 = user를 등록
    UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName,encoder.encode(password)));


    return User.fromEntity(userEntity);
  }

  // TODO : implement
  public String login(String userName, String password) {
    //회원 가입 여부 체크
    UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND,String.format("%s not founded", userName)));

    // 비밀번호 체크
    if (!encoder.matches(password, userEntity.getPassword())) {
      throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD);
    }

    // 토큰 생성

    return JwtTokenUtils.generateToken(userName,secretKey, expiredTimeMs);
  }
}
