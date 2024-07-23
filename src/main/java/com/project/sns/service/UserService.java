package com.project.sns.service;

import com.project.sns.exception.ErrorCode;
import com.project.sns.exception.SnsApplicationException;
import com.project.sns.model.Alarm;
import com.project.sns.model.User;
import com.project.sns.model.entity.UserEntity;
import com.project.sns.repository.AlarmEntityRepository;
import com.project.sns.repository.UserCacheRepository;
import com.project.sns.repository.UserEntityRepository;
import com.project.sns.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserEntityRepository userEntityRepository;
  private final UserCacheRepository userCacheRepository;
  private final AlarmEntityRepository alarmEntityRepository;
  private final BCryptPasswordEncoder encoder;

  @Value("${jwt.secret-key}")
  private String secretKey ="";

  @Value("${jwt.token.expired-time-ms}")
  private Long expiredTimeMs = 0L;


  public User loadUserByUserName(String userName) {
    //cache에 user 정보가 없을수 있으므로 getUSer 타입을 Optional로 한다음 정보가 없을 경우 orElseGet을 사용하여 DB 에서 데이터 조회를 하는 로직
    return userCacheRepository.getUser(userName).orElseGet(() ->
     userEntityRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(() ->
            new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)))
    );
  }

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

    //Cache를 이용하여 user 정보 가져옴
    //User user = loadUserByUserName(userName);
    //userCacheRepository.setUser(user);
    // 비밀번호 체크
    if (!encoder.matches(password, userEntity.getPassword())) {
      throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD);
    }

    // 토큰 생성
    return JwtTokenUtils.generateToken(userName,secretKey, expiredTimeMs);
  }

  // TODO : alarm return
  public Page<Alarm> alarmList(String userName, Pageable pageable){
    UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND,String.format("%s not founded", userName)));

    return alarmEntityRepository.findAllByUser(userEntity, pageable).map(Alarm::fromEntity);
  }
}
