package com.project2021.domain.search;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface SearchRepository extends JpaRepository<Search,Long> {
    @Query(value = "select keyword as keyword, create_Date_Time as createDateTime  from search where user_id=:userId order by create_Date_Time desc" , nativeQuery = true)
    public List findByUserId(@Param("userId") String userId);

    @Query(value = "select keyword as keyword ,count(keyword) as cnt from search where rownum <=10  group by keyword order by cnt desc" , nativeQuery = true)
    public List findByBestKeyword();

}

