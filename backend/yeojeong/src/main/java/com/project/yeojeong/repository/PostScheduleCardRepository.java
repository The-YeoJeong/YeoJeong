package com.project.yeojeong.repository;

import com.project.yeojeong.entity.Post;
import com.project.yeojeong.entity.PostDateCard;
import com.project.yeojeong.entity.PostScheduleCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostScheduleCardRepository extends JpaRepository<PostScheduleCard, Integer> {

    List<PostScheduleCard> getAllByPostDatecard(PostDateCard postDateCard);
}
