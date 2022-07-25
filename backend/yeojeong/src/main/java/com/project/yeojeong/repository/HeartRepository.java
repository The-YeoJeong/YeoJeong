package com.project.yeojeong.repository;

import com.project.yeojeong.entity.Heart;
import com.project.yeojeong.entity.Member;
import com.project.yeojeong.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Integer> {
    Heart getByPostAndMember(Post post, Member member);
}
