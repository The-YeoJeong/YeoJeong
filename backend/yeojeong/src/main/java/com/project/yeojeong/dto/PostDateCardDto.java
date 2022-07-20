package com.project.yeojeong.dto;

import com.project.yeojeong.entity.PostDateCard;
import com.project.yeojeong.entity.PostScheduleCard;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.List;

@Getter
@Setter
public class PostDateCardDto {
    private int postDatecardNo;
    private String postDateCardTitle;
    private List<PostScheduleCardDto> postScheduleCard;
//    private static ModelMapper modelMapper = new ModelMapper();
//    public static PostDateCardDto of(PostDateCard postDateCard){
//        return modelMapper.map(postDateCard, PostDateCardDto.class);
  //  }
}
