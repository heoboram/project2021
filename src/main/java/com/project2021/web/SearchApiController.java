package com.project2021.web;


import com.project2021.service.search.SearchService;
import com.project2021.service.user.UserService;
import com.project2021.web.config.UserResResult;
import com.project2021.web.config.UserResponse;
import com.project2021.web.dto.SearchSaveRequestDto;
import lombok.RequiredArgsConstructor;

import lombok.var;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;




@RequiredArgsConstructor
@RestController
public class SearchApiController {

    @Autowired
    SearchService searchService;


    @Autowired
    UserService userService;


    //오픈 검색 API
    @GetMapping("/api/search/{userId}/{keyword}")
    public ResponseEntity<Object> search(@PathVariable("userId")  String userId,
                                         @PathVariable("keyword") String keyword){

        //userId CHECK
        var check = userService.findByUserId(userId);
        if(check.equals(1)) {

            //히스토리 등록
            SearchSaveRequestDto dto = new SearchSaveRequestDto();
            dto.userId = userId;
            dto.keyword = keyword;
            searchService.save(dto);

            return searchService.findByKeyword(keyword);
        }else{
            return new ResponseEntity<>(new UserResponse(UserResResult.fail, "Not found user data"), HttpStatus.BAD_REQUEST);
        }

    }

    //나의 히스토리 최신순
    @GetMapping("/api/search/history/{userId}")
    public ResponseEntity<Object> searchHistory(@PathVariable("userId") String userId ){

       var list = searchService.findByUserIdHistory(userId);

        if( list.size() ==0){
            return new ResponseEntity<>(new UserResponse(UserResResult.fail, "Not found user data"), HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(list,HttpStatus.OK);
        }
    }

    //인기 키워드 목록
    @GetMapping("/api/search/bestSearch")
    public ResponseEntity<Object> searchBest(){

        var list = searchService.findByBestSearch() ;

        if( list.size() ==0){
            return new ResponseEntity<>(new UserResponse(UserResResult.fail, "Not found user data"), HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(list,HttpStatus.OK);
        }

    }



    }
