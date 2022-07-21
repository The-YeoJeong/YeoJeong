package com.project.yeojeong.repository;

import com.project.yeojeong.entity.Post;
import com.project.yeojeong.entity.PostDateCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PatchMapping;

public interface PostDateCardRepository extends JpaRepository<PostDateCard, Integer> {
    Integer deleteAllByPost(Post post);
}
