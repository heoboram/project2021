/*
키워드 검색 테이블
 */
package com.project2021.domain.search;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Search {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotEmpty
    @Column(length = 100 ,  nullable = false)
    private String keyword;


    @NotNull
    @NotEmpty
    //회원 ID
    @Column(length = 100 ,  nullable = false)
    private String userId;


    @CreationTimestamp
    public LocalDateTime createDateTime;




    @Builder
    public Search(String keyword, String userId , LocalDateTime createDateTime ){
        this.keyword = keyword;
        this.userId = userId;
        this.createDateTime = createDateTime;

    }
}
