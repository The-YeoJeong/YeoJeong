package com.project.yeojeong.repository;

import com.project.yeojeong.entity.Post;
import com.project.yeojeong.entity.PostDateCard;
import com.project.yeojeong.entity.PostScheduleCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostScheduleCardRepository extends JpaRepository<PostScheduleCard, Integer> {
    @Query(value = "select * from travel_post_schedulecard d where d.post_datecard_no = :postDatecardNo order by d.post_schedulecard_no", nativeQuery = true)
    List<PostScheduleCard> getAllByPostDatecard(@Param("postDatecardNo") int postDatecardNo);
}
