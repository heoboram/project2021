package com.project2021.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface UserRepository extends JpaRepository<User,Long> {


@Query(value = "select count(*) as count from user where user_id=:userId and password=:password", nativeQuery = true)
public Integer findByUserIdAndPassword(@Param("userId") String userId, @Param("password") String password);

@Query(value = "select count(*) as count from user where user_id=:userId" , nativeQuery = true)
public Integer findByUserId(@Param("userId") String userId);
}
