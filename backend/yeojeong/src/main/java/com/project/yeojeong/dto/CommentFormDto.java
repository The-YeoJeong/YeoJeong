package com.project.yeojeong.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentFormDto {
    private int commentNo;
    private int postNo;
    private int memberId;
    private String commentContent;
}
