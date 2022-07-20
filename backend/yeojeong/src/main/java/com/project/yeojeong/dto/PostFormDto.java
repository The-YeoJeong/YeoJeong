package com.project.yeojeong.dto;

import com.project.yeojeong.entity.Member;
import com.project.yeojeong.entity.Post;
import com.project.yeojeong.entity.PostDateCard;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

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
    private boolean postOnlyMe;
    public boolean getPostOnlyMe() {
        return postOnlyMe;
    }
    private List<String> postRegionName = new ArrayList<>();
    private List<PostDateCardDto> postDateCard = new ArrayList<>();
//    private static ModelMapper modelMapper = new ModelMapper();
//    public Post createPost(){
//        return modelMapper.map(this, Post.class);
//    }
//    public static PostFormDto of(Post post){
//        return modelMapper.map(post,PostFormDto.class);
//    }


}
