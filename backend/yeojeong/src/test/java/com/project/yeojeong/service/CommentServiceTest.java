package com.project.yeojeong.service;

import com.project.yeojeong.dto.CommentFormDto;
import com.project.yeojeong.entity.Comment;
import com.project.yeojeong.entity.Member;
import com.project.yeojeong.entity.Post;
import com.project.yeojeong.repository.CommentRepository;
import com.project.yeojeong.repository.MemberRepository;
import com.project.yeojeong.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CommentServiceTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("댓글 등록 테스트")
    public void createCommentTest() {
        Member member = memberRepository.getReferenceById(44);
        Post post = postRepository.getReferenceById(15);

        CommentFormDto commentFormDto = new CommentFormDto();
        commentFormDto.setCommentContent("댓글입니다.");
        Comment comment = Comment.createComment(commentFormDto,member,post);
        commentRepository.save(comment);

        assertEquals(commentFormDto.getCommentContent(),comment.getCommentContent());
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    public void updateCommentTest() {
        Member member = memberRepository.getReferenceById(44);
        Post post = postRepository.getReferenceById(15);

        CommentFormDto commentFormDto = new CommentFormDto();
        commentFormDto.setCommentContent("댓글입니다.");
        Comment comment = Comment.createComment(commentFormDto,member,post);

        commentFormDto.setCommentContent("댓글 수정입니다.");
        comment.updateComment(commentFormDto);

        assertEquals(commentFormDto.getCommentContent(),comment.getCommentContent());
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    public void deleteCommentTest() {
        Member member = memberRepository.getReferenceById(44);
        Post post = postRepository.getReferenceById(15);

        memberRepository.save(member);
        postRepository.save(post);

        CommentFormDto commentFormDto = new CommentFormDto();
        commentFormDto.setCommentContent("댓글입니다.");
        Comment comment = Comment.createComment(commentFormDto,member,post);
        commentRepository.save(comment);

        int commentNo = comment.getCommentNo();

        //삭제되었나?
        try {
            commentRepository.deleteById(commentNo);
            System.out.println("삭제되었습니다.");
        } catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    @DisplayName("댓글 리스트 태스트")
    public void commentListTest(){
        Member member = memberRepository.getReferenceById(44);
        Post post = postRepository.getReferenceById(15);

        memberRepository.save(member);
        postRepository.save(post);

        for (int i=0;i<10;i++) {
            CommentFormDto commentFormDtoCreate = new CommentFormDto();
            commentFormDtoCreate.setCommentContent("댓글입니다."+i);
            Comment comment = Comment.createComment(commentFormDtoCreate, member, post);
            commentRepository.save(comment);
        }
        List<Comment> commentList = commentRepository.getByPostOrderByCreatedByAsc(post);
        List<CommentFormDto> commentFormDtos = new ArrayList<>();

        for (int i=0;i<commentList.size();i++){
            CommentFormDto commentFormDto = new CommentFormDto();
            commentFormDto.setCommentNo(commentList.get(i).getCommentNo());
            commentFormDto.setMemberId(commentList.get(i).getMember().getMemberId());
            commentFormDto.setMemberNickname(commentList.get(i).getMember().getMemberNickname());
            commentFormDto.setCommentContent(commentList.get(i).getCommentContent());
            commentFormDto.setCreatedTime(commentList.get(i).getCreatedTime());
            System.out.println("번호"+commentFormDto.getCommentNo());
            System.out.println("내용 : "+commentFormDto.getCommentContent());
            commentFormDtos.add(commentFormDto);
        }
    }
}