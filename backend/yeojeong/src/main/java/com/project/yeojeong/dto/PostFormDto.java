package com.project.yeojeong.dto;

import com.project.yeojeong.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PostFormDto {
    private int postNo;
    private int memberNo;
    private String postTitle;
    private Date postStartDate;
    private Date postEndDate;
    private String postContent;
    private int postOnlyMe;
    private List<String> postRegionName = new ArrayList<>();
    private List<PostDateCardDto> postDateCard = new ArrayList<>();
}
