package com.project2021.web.dto;

import com.project2021.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;


@Getter
@NoArgsConstructor
public class UserSaveRequestDto {

    public String userId;

    public String password;

    public String userName;



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
