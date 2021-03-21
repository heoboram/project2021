/*
사용자 Service
 */

package com.project2021.service.user;

import com.project2021.domain.user.User;
import com.project2021.domain.user.UserRepository;

import com.project2021.web.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@RequiredArgsConstructor
@Service
public class UserService {

     private final UserRepository userRepository;

    //등록
    @Transactional
    public User save(UserSaveRequestDto requestDto){

        return userRepository.save(requestDto.toEntity());
    }



    @Transactional(readOnly = true)
    public Integer findByUserIdAndPassword(String userId, String password) {


        return userRepository.findByUserIdAndPassword(userId, password);
    }

    @Transactional(readOnly = true)
    public Integer findByUserId(String userId) {

        return userRepository.findByUserId(userId);
    }


}
