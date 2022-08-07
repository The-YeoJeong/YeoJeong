package com.project.yeojeong.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostDateCardDto {
    private int postDatecardNo;
    private String postDateCardTitle;
    private List<PostScheduleCardDto> postScheduleCard;

}
