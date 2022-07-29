package com.project.yeojeong.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {
    private int postNo;
    private String memberId;
    private String memberNickname;
    private String postTitle;
    private LocalDateTime createdTime;
    private int postHeartCnt;
    private String filePath;
}