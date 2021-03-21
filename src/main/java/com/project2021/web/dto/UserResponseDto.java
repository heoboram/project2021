package com.project2021.web.dto;

import com.project2021.domain.user.User;
import lombok.Getter;

@Getter
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
