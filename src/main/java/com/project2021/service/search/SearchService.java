/*
키워드 검색 Service
 */

package com.project2021.service.search;

import com.project2021.api.search.SearchApiClient;
import com.project2021.domain.search.Search;
import com.project2021.domain.search.SearchRepository;

import com.project2021.web.dto.SearchSaveRequestDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class SearchService {

    private final SearchApiClient searchApiClient;

    private final SearchRepository searchRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findByKeyword(String keyword){

        return   searchApiClient.requestSearch(keyword);
    }

    //등록
    @Transactional
    public Search save(SearchSaveRequestDto requestDto){

        return searchRepository.save(requestDto.toEntity());
    }

    @Transactional(readOnly = true)
    public List findByUserId(String userId){
        return searchRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List findByBestSearch(){
        return searchRepository.findByBestKeyword();
    }


}
