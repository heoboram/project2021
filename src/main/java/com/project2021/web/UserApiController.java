package com.project2021.web;


import com.project2021.domain.user.User;
import com.project2021.service.user.UserService;

import com.project2021.web.config.EncryptionUtils;
import com.project2021.web.config.UserResResult;
import com.project2021.web.config.UserResponse;
import com.project2021.web.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.MissingFormatArgumentException;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    @Autowired
    UserService userService;


    //회원가입
    @PostMapping("/api/users")
    public ResponseEntity<UserResponse> save(@RequestBody @Valid UserSaveRequestDto requestDto)throws Exception{
        try {
            //패스워드 암호화
            requestDto.password = EncryptionUtils.encryptSHA256(requestDto.getPassword());
            //회원 등록
            User user = userService.save(requestDto);

            if (user.getUserId() != null) {
                return new ResponseEntity<>(new UserResponse(UserResResult.success, ""), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new UserResponse(UserResResult.fail, "Not save user data"), HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            return new ResponseEntity<>(new UserResponse(UserResResult.fail, "Not save user data"), HttpStatus.BAD_REQUEST);
        }
   }


   // 로그인
    @GetMapping("/api/users/login/{userId}")
    public ResponseEntity<UserResponse> findByUserIdAndPassword (@PathVariable String userId,
                                                                 @RequestParam(value = "password") String password){
        if(userId == "" || password =="")
        {
            throw new MissingFormatArgumentException("Missing arguments");
        }

        var userData = userService.findByUserIdAndPassword(userId,EncryptionUtils.encryptSHA256(password));
        if(userData.equals(1))
        {
            return new ResponseEntity<>(new UserResponse(UserResResult.success, ""), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(new UserResponse(UserResResult.fail, "Not found user data"), HttpStatus.BAD_REQUEST);
        }
    }




}
