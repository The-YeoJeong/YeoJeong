package com.project.yeojeong.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PostFormDto {
    private int postNo;
    private String memberId;
    private String memberNickname;
    private String postTitle;
    private LocalDateTime createdTime;
    private boolean isLiked;
    private int heartCnt;
    private Date postStartDate;
    private Date postEndDate;
    private String postContent;
    private boolean postOnlyMe;
    private List<String> postRegionName = new ArrayList<>();
    private List<PostDateCardDto> postDateCard = new ArrayList<>();

}
