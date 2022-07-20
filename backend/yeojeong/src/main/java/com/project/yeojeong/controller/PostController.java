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
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String sdate = "20200902";
        String edate = "20200904";
        Date s_date = format.parse(sdate);
        Date e_date = format.parse(edate);

        postDto.setPostTitle("글 제목");
        List<String> regionname = new ArrayList<>();
        regionname.add("광주");
        regionname.add("충북");
        postDto.setPostRegionName(regionname);
        postDto.setPostStartDate(s_date);
        postDto.setPostEndDate(e_date);
        postDto.setPostContent("후기");
        postDto.setPostOnlyMe(true);

        List<PostDateCardDto> postDateCardDto = new ArrayList<>();
        postDateCardDto.get(0).setPostDateCardTitle("일자카드 제목1");
        postDateCardDto.get(1).setPostDateCardTitle("일자카드 제목2");

        List<PostScheduleCardDto> postScheduleCards = new ArrayList<>();
        postScheduleCards.get(0).setPlaceName("카페");
        postScheduleCards.get(0).setPlaceAddress("광주 어쩌고 1층");
        postScheduleCards.get(0).setPlaceContent("아아 먹어야지");

        postDateCardDto.get(0).setPostScheduleCard(postScheduleCards);
        int postNo = postService.postnew(postDto, principal);
        return new ResponseEntity<>(postNo,HttpStatus.OK);
    }
}
