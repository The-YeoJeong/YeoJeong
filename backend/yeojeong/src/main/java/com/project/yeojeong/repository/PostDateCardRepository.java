package com.project.yeojeong.repository;

import com.project.yeojeong.entity.Post;
import com.project.yeojeong.entity.PostDateCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostDateCardRepository extends JpaRepository<PostDateCard, Integer> {
    Integer deleteAllByPost(Post post);
    @Query(value = "select * from travel_post_datecard d where d.post_no = :postNo order by d.post_datecard_no", nativeQuery = true)
    List<PostDateCard> getAllByPost(@Param("postNo") int postNo);
}
