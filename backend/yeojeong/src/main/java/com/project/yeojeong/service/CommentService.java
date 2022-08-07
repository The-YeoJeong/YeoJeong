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

    public CommentFormDto commentnew(CommentFormDto commentFormDto, Principal principal) {
        Member member = memberRepository.getByMemberId(principal.getName());
        Post post = postRepository.getReferenceById(commentFormDto.getPostNo());
        Comment comment = Comment.createComment(commentFormDto, member, post);

        Comment comment1  = commentRepository.save(comment);
        CommentFormDto commentFormDto1 = new CommentFormDto();
        commentFormDto1.setCommentNo(comment1.getCommentNo());
        commentFormDto1.setCommentContent(comment1.getCommentContent());
        commentFormDto1.setCreatedTime(comment1.getCreatedTime());
        commentFormDto1.setMemberNickname(member.getMemberNickname());
        commentFormDto1.setPostNo(comment1.getPost().getPostNo());
        commentFormDto1.setMemberId(member.getMemberId());

        return commentFormDto1;
    }

    public void commentEdit(int commentNo, CommentFormDto commentFormDto) {
        Comment comment = commentRepository.getReferenceById(commentNo);
        comment.updateComment(commentFormDto);
    }

    public List<CommentFormDto> commentlist(int postNo) {
        Post post = postRepository.getReferenceById(postNo);
        List<Comment> commentList = commentRepository.getByPostOrderByCreatedByAsc(post);
        List<CommentFormDto> commentFormDtos = new ArrayList<>();

        for (int i=0;i<commentList.size();i++){
            CommentFormDto commentFormDto = new CommentFormDto();
            commentFormDto.setCommentNo(commentList.get(i).getCommentNo());
            commentFormDto.setMemberId(commentList.get(i).getMember().getMemberId());
            commentFormDto.setMemberNickname(commentList.get(i).getMember().getMemberNickname());
            commentFormDto.setCommentContent(commentList.get(i).getCommentContent());
            commentFormDto.setCreatedTime(commentList.get(i).getCreatedTime());
            commentFormDtos.add(commentFormDto);
        }

        return commentFormDtos;
    }
}
