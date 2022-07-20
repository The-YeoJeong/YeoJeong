package com.project.yeojeong.dto;

import com.project.yeojeong.entity.PostScheduleCard;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostDateCardDto {
    private String postDateCardTitle;
    private List<PostScheduleCardDto> postScheduleCard;
}
