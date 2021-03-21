package com.project2021.web.dto;

import com.project2021.domain.search.Search;
import com.project2021.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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
