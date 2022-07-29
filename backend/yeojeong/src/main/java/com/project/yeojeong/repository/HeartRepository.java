package com.project.yeojeong.repository;

import com.project.yeojeong.entity.Heart;
import com.project.yeojeong.entity.Member;
import com.project.yeojeong.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Integer> {
    Heart getByPostAndMember(Post post, Member member);

    @Query(value = "SELECT h.post_no FROM heart h WHERE h.member_no = :memberNo", nativeQuery = true )
    Integer[] findPostNoByMemberNo(@Param("memberNo") int memberNo);

}