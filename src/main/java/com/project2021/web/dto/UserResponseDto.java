package com.project2021.web.dto;

import com.project2021.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

//모든 필드의 get 메소드 생성
@Getter
//기본생성자 자동 추가
@NoArgsConstructor
public class UserResponseDto {

    public String userId;

    public String password;

    public String userName;

    public UserResponseDto(User entity){
        this.userId = entity.getUserId();
        this.password = entity.getPassword();
        this.userName = entity.getUserName();
    }
}
