package com.project.yeojeong.controller;

import com.project.yeojeong.dto.CommentFormDto;
import com.project.yeojeong.dto.PostFormDto;
import com.project.yeojeong.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.xml.stream.events.Comment;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping(value = "/new")
    public @ResponseBody ResponseEntity commentNew(@Valid @RequestBody CommentFormDto commentFormDto, Principal principal) {
        //로그인 했을 때
        if(principal!=null){
            int commentNo = commentService.commentnew(commentFormDto, principal);
            System.out.println("댓글 번호" + commentNo);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        //로그인 안 했을 때
        else {
            String error="로그인 하세요.";
            return new ResponseEntity<>(error,HttpStatus.OK);
        }
    }
}