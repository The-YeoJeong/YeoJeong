package com.project.yeojeong.controller;

import com.google.gson.JsonObject;
import com.project.yeojeong.dto.PostFormDto;
import com.project.yeojeong.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    //글 작성
    @PostMapping(value = "/new", produces = "application/json")
    public @ResponseBody ResponseEntity postNew(@Valid @RequestBody PostFormDto postDto){
        JsonObject json = new JsonObject();
        //json = serv
        postService.postnew(postDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
