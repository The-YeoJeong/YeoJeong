package com.project.yeojeong.repository;

import com.project.yeojeong.entity.Comment;
import com.project.yeojeong.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> getByPost(Post post);
}
