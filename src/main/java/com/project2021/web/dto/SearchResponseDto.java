package com.project2021.web.dto;

import com.project2021.domain.search.Search;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
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
