package com.project.yeojeong.controller;

import com.project.yeojeong.dto.CommentFormDto;
import com.project.yeojeong.entity.Comment;
import com.project.yeojeong.entity.Post;
import com.project.yeojeong.repository.CommentRepository;
import com.project.yeojeong.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @GetMapping(value = "/{postNo}")
    public @ResponseBody ResponseEntity commentList(@Valid @PathVariable("postNo") int postNo){
        List<CommentFormDto> comments = commentService.commentlist(postNo);
        return new ResponseEntity<>(comments,HttpStatus.OK);
    }

    @PostMapping(value = "/new")
    public @ResponseBody ResponseEntity commentNew(@Valid @RequestBody CommentFormDto commentFormDto, Principal principal) {
        int commentNo = commentService.commentnew(commentFormDto, principal);
        System.out.println("댓글 번호" + commentNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/edit/{commentNo}")
    public @ResponseBody ResponseEntity commentUpdate(@Valid @RequestBody CommentFormDto commentFormDto, @PathVariable("commentNo") int commentNo, Principal principal) {
        //작성자와 로그인한 사람이 같을 때
        Comment comment = commentRepository.getReferenceById(commentNo);
        if (principal.getName().equals(comment.getMember().getMemberId())) {
            commentService.commentEdit(commentNo,commentFormDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        //작성자와 로그인한 사람이 같지 않을 때
        else {
            String error = "지금 로그인 아이디는 작성자가 아닙니다.";
            return new ResponseEntity<>(error, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/{commentNo}")
    public @ResponseBody ResponseEntity commentDelete(@Valid @PathVariable("commentNo") int commentNo, Principal principal){
        Comment comment = commentRepository.getReferenceById(commentNo);
        //작성자와 로그인한 사람이 같을 때
        if (principal.getName().equals(comment.getMember().getMemberId())) {
            commentRepository.deleteById(commentNo);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        //작성자와 로그인한 사람이 같지 않을 때
        else {
            String error="지금 로그인 아이디는 작성자가 아닙니다.";
            return new ResponseEntity<>(error,HttpStatus.OK);
        }
    }
}