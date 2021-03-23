/*
사용자 테이블
 */
package com.project2021.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@Entity
public class User {
//User Table
        @Id //PK
        @GeneratedValue //PK생성규칙
        private Long id;

        @NotNull
        @NotEmpty
        //회원 ID
        @Column(length = 100 ,  nullable = false, unique=true)
        private String userId;

        @NotNull
        @NotEmpty
        //회원 패스워드
        @Column(length = 100 ,  nullable = false)
        private String password;

        @NotNull
        @NotEmpty
        //회원 이름
        @Column(length = 100 ,  nullable = false)
        private String userName;


        @Builder
        public User(String userId,  String password , String userName){
                this.userId = userId;
                this.password = password;
                this.userName = userName;
        }

}
