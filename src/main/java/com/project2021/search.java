package com.project2021;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//스프링부트 자동 설정
@SpringBootApplication
public class search {
    public static void main(String[] args){
        //내장 WAS 스프링부트로 만들어진 Jar 파일로 실행
        SpringApplication.run(search.class,args);
    }
}
