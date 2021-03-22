package com.project2021.web;

import com.project2021.domain.user.User;
import com.project2021.domain.user.UserRepository;
import com.project2021.web.config.EncryptionUtils;
import com.project2021.web.dto.UserResponseDto;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @After
    public void tearDown() throws Exception{
        userRepository.deleteAll();
    }

    
    //저장 TEST
    @Test
    public void save(){
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
        System.out.println("=================");
        System.out.println(user.getUserId());
        System.out.println(user.getPassword());
        System.out.println(user.getUserName());
        System.out.println("=================");

        assertThat(user.getUserId()).isEqualTo(userId);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getUserName()).isEqualTo(userName);
    }

    //회원가입 TEST
    @Test
    public void userSave(){
        String userId = "gj0612";
        String password = "1234";
        String userName = "허보람";
        UserResponseDto userDto = new UserResponseDto(User.builder().userId(userId).password(password).userName(userName).build());
        String url = "http://localhost:" + port + "api/users";
        HttpEntity<UserResponseDto> requestEntity = new HttpEntity<>(userDto);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(url, HttpMethod.POST,requestEntity,Object.class);


        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    //로그인
    @Test
    public void userLogin(){

        String userId = "gj0612";
        String password = EncryptionUtils.encryptSHA256("1234");
        String userName = "허보람";

        //로그인 TEST 회원 가입 먼저
        User saveUser =  userRepository.save(User.builder()
                .userId(userId)
                .password(password)
                .userName(userName)
                .build());

        String userId2 = saveUser.getUserId();
        String password2 = "1234";

        UserResponseDto userDto = new UserResponseDto(User.builder().userId(userId2).password(password2).build());
        //로그인


        String url = "http://localhost:" + port + "api/users/login/" +userId2 +"?"+"password=" +password2;

        HttpEntity<UserResponseDto> requestEntity = new HttpEntity<>(userDto);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(url, HttpMethod.GET,requestEntity,Object.class);


        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

}
