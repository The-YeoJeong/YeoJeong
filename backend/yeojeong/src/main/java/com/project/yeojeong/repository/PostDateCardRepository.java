package com.project.yeojeong.repository;

import com.project.yeojeong.entity.Post;
import com.project.yeojeong.entity.PostDateCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostDateCardRepository extends JpaRepository<PostDateCard, Integer> {
    Integer deleteAllByPost(Post post);

    List<PostDateCard> getAllByPost(Post post);
}
