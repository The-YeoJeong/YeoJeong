package com.project.yeojeong.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class PostDto {
    private int postNo;
    private String memberId;
    private String memberNickname;
    private String postTitle;
    private LocalDateTime createdTime;
    private int postHeartCnt;
    private String filePath;
}