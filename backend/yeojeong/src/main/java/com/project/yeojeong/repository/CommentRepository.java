package com.project.yeojeong.repository;

import com.project.yeojeong.entity.Comment;
import com.project.yeojeong.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
