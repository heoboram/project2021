package com.project2021.domain.search;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Map;

@Repository
public interface SearchRepository extends JpaRepository<Search,Long> {


    // 내 검색 히스토리
    // 키워드 , 검색일시를 최신순으로
    @Query(value = "select keyword as keyword, create_Date_Time as createDateTime  from search where user_id=:userId order by create_Date_Time desc" , nativeQuery = true)
    public List<Map<String,String>>  findByUserIdHistory(@Param("userId") String userId);

    //인기 키워드 목록 사용자들이 많이 검색한 순서대로 최대 10개의 검색 키워드
    //키워드 별로 검색된 횟수도 표기
    @Query(value = "select  TOP 10 keyword as keyword ,count(keyword) as count from search  group by keyword order by count desc " , nativeQuery = true)
    public List<Map<String,String>> findByBestKeyword();


}

