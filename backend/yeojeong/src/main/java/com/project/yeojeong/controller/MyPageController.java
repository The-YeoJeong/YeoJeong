package com.project.yeojeong.controller;

import com.project.yeojeong.dto.PostDto;
import com.project.yeojeong.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {
    private final PostService postService;


    @GetMapping(value = "/post/{section}/filter")
    public @ResponseBody ResponseEntity<List<PostDto>> myPostList(@PathVariable("section") int section,
                                                                  @RequestParam(value = "searchContent", required = false) String searchContent,
                                                                  @RequestParam(value = "onlyPlan", required = false) boolean onlyPlan,
                                                                  Pageable pageable, Principal principal) {

        if(section < 0 || section > 2) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(postService.myPostList(section, searchContent, onlyPlan, pageable, principal), HttpStatus.OK);
    }
}
