package com.project.yeojeong.controller;

import com.project.yeojeong.dto.ConditionDto;
import com.project.yeojeong.dto.PostDto;
import com.project.yeojeong.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {
    private final PostService postService;

    // top 3
    @GetMapping(value = "/post/top")
    public @ResponseBody ResponseEntity<List<PostDto>> postTopList() {
        return new ResponseEntity(postService.postTopList(), HttpStatus.OK);
    }

    @PostMapping("/post")
    public @ResponseBody ResponseEntity<List<PostDto>> postList(@RequestBody(required = false) ConditionDto conditionDto,
                                                                Pageable pageable) {
        return new ResponseEntity(postService.postList(conditionDto, pageable), HttpStatus.OK);
    }

}
