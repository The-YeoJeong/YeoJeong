package com.project.yeojeong.repository;

import com.project.yeojeong.entity.Post;
import com.project.yeojeong.entity.PostRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRegionRepository extends JpaRepository<PostRegion, Integer> {
    Integer deleteAllByPost(Post post);
    List<PostRegion> getAllByPost(Post post);

}
