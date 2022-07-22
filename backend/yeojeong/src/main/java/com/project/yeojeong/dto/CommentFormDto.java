package com.project.yeojeong.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentFormDto {
    private int commentNo;
    private int postNo;
    private String memberId;
    private String memberNickName;
    private String commentContent;
    private LocalDateTime createdTime;
}
