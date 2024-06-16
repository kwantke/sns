package com.project.sns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.
//jpa 프로젝트 최초 생성후 아무 설정 없이 바로 실행하면,gradle에서 추가한 JPA 실행시 database 설정을 읽으면서 나타나는 에러이다.
//그래서 exclude = DataSourceAutoConfiguration.class 를 설정하므로써 JPA 실행시 datasource 설정 않 읽으므로쎠 예외 시키는 설정이다.
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SnsApplication {

  public static void main(String[] args) {
    SpringApplication.run(SnsApplication.class, args);
  }

}
