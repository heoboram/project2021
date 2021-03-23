/*
키워드 검색 테이블
 */
package com.project2021.domain.search;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
//데이터가 많을 경우를 대비한 인덱스 설정
@Table(indexes =  {@Index(name = "i_user_id" , columnList= "userid"),@Index(name = "i_keyword", columnList = "keyword DESC")})
public class Search {

    @Id //PK
    @GeneratedValue
    private Long id;

    @NotNull
    @NotEmpty
    @Column(length = 100 ,  nullable = false)
    private String keyword; // 키워드


    @NotNull
    @NotEmpty
    //회원 ID
    @Column(length = 100 ,  nullable = false)
    private String userId;


    @CreationTimestamp
    public LocalDateTime createDateTime; //검색 시간




    @Builder
    public Search(String keyword, String userId , LocalDateTime createDateTime ){
        this.keyword = keyword;
        this.userId = userId;
        this.createDateTime = createDateTime;

    }
}
