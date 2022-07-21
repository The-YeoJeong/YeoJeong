package com.project.yeojeong.dto;

import com.project.yeojeong.entity.PostDateCard;
import com.project.yeojeong.entity.PostScheduleCard;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class PostScheduleCardDto {
    private int postSchedulecardNo;
    private String placeName;
    private String placeAddress;
    private String placeContent;
}
