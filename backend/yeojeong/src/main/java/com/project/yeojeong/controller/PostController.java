package com.project.yeojeong.controller;

import com.project.yeojeong.dto.PostDateCardDto;
import com.project.yeojeong.dto.PostFormDto;
import com.project.yeojeong.dto.PostScheduleCardDto;
import com.project.yeojeong.entity.PostScheduleCard;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    //글 작성
    @PostMapping(value = "/new")
    public @ResponseBody ResponseEntity postNew(@Valid @RequestBody PostFormDto postDto, Principal principal) throws ParseException {
        int postNo = postService.postnew(postDto, principal);
        System.out.println("글 번호"+postNo);
        return new ResponseEntity<>(postNo,HttpStatus.OK);
    }
}