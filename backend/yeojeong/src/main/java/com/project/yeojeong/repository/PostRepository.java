package com.project.yeojeong.repository;

import com.project.yeojeong.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "select * from travel_post p order by p.post_heart_cnt desc LIMIT 3"  , nativeQuery = true)
    List<Post> findTopList();

}
