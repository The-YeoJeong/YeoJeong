package com.project.yeojeong.controller;

import com.project.yeojeong.dto.PostFormDto;
import com.project.yeojeong.entity.Post;
import com.project.yeojeong.repository.MemberRepository;
import com.project.yeojeong.repository.PostRepository;
import com.project.yeojeong.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;

    //글 작성
    @PostMapping(value = "/new")
    public @ResponseBody ResponseEntity postNew(@Valid @RequestBody PostFormDto postFormDto, Principal principal) {
        //로그인 했을 때
        if (principal!=null) {
            int postNo = postService.postnew(postFormDto, principal);
            System.out.println("글 번호"+postNo);
            return new ResponseEntity<>(postNo,HttpStatus.OK);
        }
        //로그인 안 했을 때
        else {
            String error = "로그인 하세요.";
            return new ResponseEntity<>(error, HttpStatus.OK);
        }
    }

    //글 수정
    @PatchMapping(value = "/edit/{postNo}")
    public @ResponseBody ResponseEntity postEdit(@Valid @RequestBody PostFormDto postFormDto, Principal principal) {
        //작성자와 로그인한 사람이 같을 때
        if (principal.getName().equals(postFormDto.getMemberId())) {
            int postNo = postService.postedit(postFormDto, principal);
            System.out.println("글 번호" + postNo);
            return new ResponseEntity<>(postNo,HttpStatus.OK);
        }
        //로그인 안 했을 때
        else if (principal == null) {
            String error="로그인 하세요.";
            return new ResponseEntity<>(error,HttpStatus.OK);
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
        Optional<Post> post = postRepository.findById(postNo);
        //작성자와 로그인한 사람이 같을 때
        if (principal.getName().equals(post.get().getMember().getMemberId())) {
            postRepository.deleteById(postNo);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        //로그인 안 했을 때
        else if (principal == null) {
            String error="로그인 하세요.";
            return new ResponseEntity<>(error,HttpStatus.OK);
        }
        //작성자와 로그인한 사람이 같지 않을 때
        else {
            String error="지금 로그인 아이디는 작성자가 아닙니다.";
            return new ResponseEntity<>(error,HttpStatus.OK);
        }
    }
}
