package com.project2021.web.dto;

import com.project2021.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


//모든 필드의 get 메소드 생성
@Getter
//기본생성자 자동 추가
@NoArgsConstructor
public class UserSaveRequestDto {

    public String userId;

    public String password;

    public String userName;


    //빌더 패턴 클래스생성
    @Builder
    public UserSaveRequestDto(String userId, String password , String userName){


        this.userId = userId;


        this.password =  password;

        this.userName =userName ;

    }

    public User toEntity(){
        return User.builder()
                .userId(userId)
                .password(password)
                .userName(userName)
                .build();
    }


}
