package com.project.yeojeong.controller;

import com.project.yeojeong.dto.PostFormDto;
import com.project.yeojeong.entity.Post;
import com.project.yeojeong.repository.PostRepository;
import com.project.yeojeong.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;

    //글 디테일
    @GetMapping(value = "/detail/{postNo}")
    public @ResponseBody ResponseEntity postDetail(@Valid @PathVariable("postNo") int postNo, Principal principal) {
        PostFormDto postFormDto = postService.postDetail(postNo,principal);
        return new ResponseEntity<>(postFormDto,HttpStatus.OK);
    }

    //글 작성
    @PostMapping(value = "/new")
    public @ResponseBody ResponseEntity postNew(@Valid @RequestBody PostFormDto postFormDto, Principal principal) {
        int postNo = postService.postnew(postFormDto, principal);
        System.out.println("글 번호" + postNo);
        return new ResponseEntity<>(postNo, HttpStatus.OK);
    }

    //글 수정
    @PatchMapping(value = "/edit/{postNo}")
    public @ResponseBody ResponseEntity postEdit(@Valid @RequestBody PostFormDto postFormDto, @PathVariable("postNo") int postNo, Principal principal) {
        //작성자와 로그인한 사람이 같을 때
        Post post = postRepository.getReferenceById(postNo);
        if (principal.getName().equals(post.getMember().getMemberId())) {
            int postNoResult = postService.postedit(postNo,postFormDto);
            System.out.println("글 번호" + postNoResult);
            return new ResponseEntity<>(postNoResult,HttpStatus.OK);
        }
        //작성자와 로그인한 사람이 같지 않을 때
        else {
            String error="지금 로그인 아이디는 작성자가 아닙니다.";
            return new ResponseEntity<>(error,HttpStatus.OK);
        }
    }

    //글 삭제
    @DeleteMapping(value = "/{postNo}")
    public @ResponseBody ResponseEntity postDelete(@Valid @PathVariable("postNo") int postNo, Principal principal){
        Post post = postRepository.getReferenceById(postNo);
        //작성자와 로그인한 사람이 같을 때
        if (principal.getName().equals(post.getMember().getMemberId())) {
            postRepository.deleteById(postNo);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        //작성자와 로그인한 사람이 같지 않을 때
        else {
            String error="지금 로그인 아이디는 작성자가 아닙니다.";
            return new ResponseEntity<>(error,HttpStatus.OK);
        }
    }



}
