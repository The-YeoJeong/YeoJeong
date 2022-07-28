package com.project.yeojeong.controller;

import com.project.yeojeong.dto.ConditionDto;
import com.project.yeojeong.dto.MainPostDto;
import com.project.yeojeong.entity.Post;
import com.project.yeojeong.repository.PostRepository;
import com.project.yeojeong.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {
    private final PostService postService;

    // top 3
    @GetMapping(value = "/post/top")
    public @ResponseBody ResponseEntity<List<MainPostDto>> postTopList() {
        return new ResponseEntity(postService.postTopList(), HttpStatus.OK);
    }

    @PostMapping("/post")
    public @ResponseBody ResponseEntity<List<MainPostDto>> postList(@RequestBody(required = false) ConditionDto conditionDto,
                                                                    Pageable pageable) {
        Map<String, Object> body = new HashMap<>();
        List<MainPostDto> mainPostDtoList = postService.postList(conditionDto, pageable);
        body.put("postCnt", mainPostDtoList.size());
        body.put("postList", mainPostDtoList);

        return new ResponseEntity(body, HttpStatus.OK);
    }

}
