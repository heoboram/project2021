package com.project2021.web.dto;

import com.project2021.domain.search.Search;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
//모든 필드의 get 메소드 생성
@Getter
//기본생성자 자동 추가
@NoArgsConstructor
public class SearchResponseDto {
    public String userId;
    public String keyword;
    public LocalDateTime createDateTime;


    public SearchResponseDto(Search entity){
        this.userId = entity.getUserId();
        this.keyword = entity.getKeyword();
        this.createDateTime = entity.getCreateDateTime();
    }
}
