package com.project.yeojeong.repository;

import com.project.yeojeong.entity.Member;
import com.project.yeojeong.entity.Post;
import com.project.yeojeong.entity.PostRegion;
import com.project.yeojeong.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRegionRepository extends JpaRepository<PostRegion, Integer> {
    Integer deleteAllByPost(Post post);
}
