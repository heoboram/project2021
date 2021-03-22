package com.project2021.web;

import com.project2021.domain.search.Search;
import com.project2021.domain.search.SearchRepository;
import com.project2021.domain.user.User;
import com.project2021.domain.user.UserRepository;
import com.project2021.web.config.EncryptionUtils;
import com.project2021.web.dto.SearchResponseDto;

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



import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SearchApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SearchRepository searchRepository;

    @After
    public void tearDown() throws Exception{
        userRepository.deleteAll();
        searchRepository.deleteAll();
    }

    
    //검색 키워드 
    @Test
    public void search(){
        String userId = "gj0612";
        String password = EncryptionUtils.encryptSHA256("1234");
        String userName = "허보람";

        //회원 가입 먼저
        User saveUser =  userRepository.save(User.builder()
                .userId(userId)
                .password(password)
                .userName(userName)
                .build());

        String userId2 = saveUser.getUserId();
        String keyword = "주차장";

        SearchResponseDto searDto = new SearchResponseDto(Search.builder().userId(userId2).keyword(keyword).build());

        String url = "http://localhost:" + port + "api/search/" +userId2 +"/"+keyword;

        HttpEntity<SearchResponseDto> requestEntity = new HttpEntity<>(searDto);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(url, HttpMethod.GET,requestEntity,Object.class);


        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }


    //나의 히스토리 최신순
    @Test
    public void userSearchHistory(){

        String userId = "gj0612";
        String password = EncryptionUtils.encryptSHA256("1234");
        String userName = "허보람";

        //회원 가입 먼저
        User saveUser =  userRepository.save(User.builder()
                .userId(userId)
                .password(password)
                .userName(userName)
                .build());

        String userId2 = saveUser.getUserId();
        String keyword = "주차장";
        //회원가입 후 키워드 검색하여 히스토리담고..//
        SearchResponseDto searDto = new SearchResponseDto(Search.builder().userId(userId2).keyword(keyword).build());

        String url = "http://localhost:" + port + "api/search/" +userId2 +"/"+keyword;

        HttpEntity<SearchResponseDto> requestEntity = new HttpEntity<>(searDto);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(url, HttpMethod.GET,requestEntity,Object.class);


        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);


        String url2 = "http://localhost:" + port + "api/search/history/" +userId2 ;
        HttpEntity<SearchResponseDto> requestEntity2 = new HttpEntity<>(searDto);
        ResponseEntity<Object> responseEntity2 = restTemplate.exchange(url, HttpMethod.GET,requestEntity2,Object.class);


        assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
    //인기 키워드 목록
    @Test
    public void userSearchBest(){

        String userId = "gj0612";
        String password = EncryptionUtils.encryptSHA256("1234");
        String userName = "허보람";

        //회원 가입 먼저
        User saveUser =  userRepository.save(User.builder()
                .userId(userId)
                .password(password)
                .userName(userName)
                .build());

        String userId2 = saveUser.getUserId();
        String keyword = "주차장";

        //회원가입 후 키워드 검색하여 히스토리담고..//
        SearchResponseDto searDto = new SearchResponseDto(Search.builder().userId(userId2).keyword(keyword).build());

        String url = "http://localhost:" + port + "api/search/" +userId2 +"/"+keyword;

        HttpEntity<SearchResponseDto> requestEntity = new HttpEntity<>(searDto);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(url, HttpMethod.GET,requestEntity,Object.class);


        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        //인기 키워드 목록
        String url2 = "http://localhost:" + port + "api/search/bestSearch" ;
        HttpEntity<SearchResponseDto> requestEntity2 = new HttpEntity<>(searDto);
        ResponseEntity<Object> responseEntity2 = restTemplate.exchange(url, HttpMethod.GET,requestEntity2,Object.class);

        assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

}
