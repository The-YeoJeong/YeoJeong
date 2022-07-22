package com.project.yeojeong.service;

import com.project.yeojeong.dto.CommentFormDto;
import com.project.yeojeong.entity.Comment;
import com.project.yeojeong.entity.Member;
import com.project.yeojeong.entity.Post;
import com.project.yeojeong.repository.CommentRepository;
import com.project.yeojeong.repository.MemberRepository;
import com.project.yeojeong.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public int commentnew(CommentFormDto commentFormDto, Principal principal) {
        Member member = memberRepository.getByMemberId(principal.getName());
        Post post = postRepository.getReferenceById(commentFormDto.getPostNo());
        Comment comment = Comment.createComment(commentFormDto, member, post);
        int commentNo = commentRepository.save(comment).getCommentNo();
        return commentNo;
    }

    public void commentEdit(int commentNo, CommentFormDto commentFormDto) {
        Comment comment = commentRepository.getReferenceById(commentNo);
        comment.updateComment(commentFormDto);
    }

    public List<CommentFormDto> commentlist(int postNo) {
        Post post = postRepository.getReferenceById(postNo);
        List<Comment> commentList = commentRepository.getByPost(post);
        List<CommentFormDto> commentFormDtos = new ArrayList<>();

        for (int i=0;i<commentList.size();i++){
            commentFormDtos.get(i).setCommentNo(commentList.get(i).getCommentNo());
            commentFormDtos.get(i).setMemberId(commentList.get(i).getMember().getMemberId());
            commentFormDtos.get(i).setMemberNickName(commentList.get(i).getMember().getMemberNickname());
            commentFormDtos.get(i).setCommentContent(commentList.get(i).getCommentContent());
            commentFormDtos.get(i).setCreatedTime(commentList.get(i).getCreatedTime());
        }

        return commentFormDtos;
    }
}
