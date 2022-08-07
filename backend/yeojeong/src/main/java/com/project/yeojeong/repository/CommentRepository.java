package com.project.yeojeong.repository;

import com.project.yeojeong.entity.Comment;
import com.project.yeojeong.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> getByPostOrderByCreatedByAsc(Post post);

    @Query(value = "SELECT c.post_no FROM comment c WHERE c.member_no = :memberNo", nativeQuery = true )
    Integer[] findPostNoByMemberNo(@Param("memberNo") int memberNo);
}
