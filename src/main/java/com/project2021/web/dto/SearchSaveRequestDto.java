package com.project2021.web.dto;

import com.project2021.domain.search.Search;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//모든 필드의 get 메소드 생성
@Getter
//기본생성자 자동 추가
@NoArgsConstructor
public class SearchSaveRequestDto {
    public String userId;
    public String keyword;

    @Builder
    public SearchSaveRequestDto(String userId, String keyword ){
        this.userId = userId;
        this.keyword =  keyword;
    }

    public Search toEntity(){
        return Search.builder()
                .userId(userId)
                .keyword(keyword)
                .build();
    }

}
