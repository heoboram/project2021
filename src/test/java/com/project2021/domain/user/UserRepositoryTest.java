package com.project2021.domain.user;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @After
    public void cleanup() {
        userRepository.deleteAll();
    }

  @Test
    public void 회원가입(){
         String userId = "gj0612";
         String password = "1234";
         String userName = "허보람";


        userRepository.save(User.builder()
                .userId(userId)
                .password(password)
                .userName(userName)
                .build());

        //when
        //테이블 에 있는 모든 데이터를 조회해오는 메소드
        List<User> list = userRepository.findAll();

        User user = list.get(0);
        assertThat(user.getUserId()).isEqualTo(userId);
         assertThat(user.getPassword()).isEqualTo(password);
      assertThat(user.getUserName()).isEqualTo(userName);
    }

}

