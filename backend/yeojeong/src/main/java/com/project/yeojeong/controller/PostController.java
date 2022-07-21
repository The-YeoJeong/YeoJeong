package com.project.yeojeong.controller;

import com.project.yeojeong.dto.PostDateCardDto;
import com.project.yeojeong.dto.PostFormDto;
import com.project.yeojeong.dto.PostScheduleCardDto;
import com.project.yeojeong.entity.Post;
import com.project.yeojeong.entity.PostScheduleCard;
import com.project.yeojeong.repository.MemberRepository;
import com.project.yeojeong.repository.PostRepository;
import com.project.yeojeong.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.Charset;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    //글 작성
    @PostMapping(value = "/new")
    public @ResponseBody ResponseEntity postNew(@Valid @RequestBody PostFormDto postFormDto, Principal principal) {
        int postNo = postService.postnew(postFormDto, principal);
        System.out.println("글 번호"+postNo);
        return new ResponseEntity<>(postNo,HttpStatus.OK);
    }

    //글 수정
    @PatchMapping(value = "/edit/{postNo}")
    public @ResponseBody ResponseEntity postEdit(@Valid @RequestBody PostFormDto postFormDto, Principal principal) {
        if (principal.getName().equals(postFormDto.getMemberId())) {
            int postNo = postService.postedit(postFormDto, principal);
            System.out.println("글 번호" + postNo);
            return new ResponseEntity<>(postNo,HttpStatus.OK);
        } else {
            String error="지금 로그인 아이디는 작성자가 아닙니다.";
            return new ResponseEntity<>(error,HttpStatus.OK);
        }
    }

    //글 삭제
    @DeleteMapping(value = "/{postNo}")
    public @ResponseBody ResponseEntity postDelete(@Valid @PathVariable("postNo") int postNo, Principal principal){
        Optional<Post> post = postRepository.findById(postNo);
        if (principal.getName().equals(post.get().getMember().getMemberId())) {
            postRepository.deleteById(postNo);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            String error="지금 로그인 아이디는 작성자가 아닙니다.";
            return new ResponseEntity<>(error,HttpStatus.OK);
        }
    }
}
