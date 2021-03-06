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
//JSON을 반환하는 컨트롤러
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
                return new ResponseEntity<>(new UserResponse(UserResResult.success, "password : " +user.getPassword()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new UserResponse(UserResResult.fail, "Not save user data"), HttpStatus.BAD_REQUEST);
            }

        }catch(Exception e){
            return new ResponseEntity<>(new UserResponse(UserResResult.fail, "Not save user data"), HttpStatus.BAD_REQUEST);
        }
   }


    // 로그인
    //Http Method인 Get 요청 API
    @GetMapping("/api/users/login/{userId}")
    public ResponseEntity<UserResponse> findByUserIdAndPassword (@PathVariable String userId,
                                                                 @RequestParam(value = "password") String password){


        //로그인 요청url id 또는 pw 값이 없을 경우
        if(userId == "" || password =="")
        {
            throw new MissingFormatArgumentException("Missing arguments");
        }
        //해당 id 와 pw 가 테이블에 등록되어있는지 check
        var userData = userService.findByUserIdAndPassword(userId,EncryptionUtils.encryptSHA256(password));
        if(userData.equals(1))
        {
            return new ResponseEntity<>(new UserResponse(UserResResult.success,"login Success" ), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(new UserResponse(UserResResult.fail, "Not found user data"), HttpStatus.BAD_REQUEST);
        }
    }




}
