package com.project.yeojeong.service;

import com.project.yeojeong.dto.MainPostDto;
import com.project.yeojeong.dto.PostFormDto;
import com.project.yeojeong.entity.*;
import com.project.yeojeong.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final UploadFileRepository uploadFileRepository;
    private final PostDateCardRepository postDateCardRepository;
    private final PostScheduleCardRepository postScheduleCardRepository;
    private final PostRegionRepository postRegionRepository;
    private final RegionRepository regionRepository;
    //글 작성
    public int postnew(PostFormDto postFormDto, Principal principal) {
        Member member = memberRepository.getByMemberId(principal.getName());
        //전체 글 저장
        Post post = Post.createPost(postFormDto, member);
        int postNo = postRepository.save(post).getPostNo();
        //글 해당 지역 저장
        for (int i=0;i<postFormDto.getPostRegionName().size();i++) {
            PostRegion postRegion = new PostRegion();
            postRegion.setPost(post);
            Region region = regionRepository.getByRegionName(postFormDto.getPostRegionName().get(i));
            postRegion.setRegion(region);
            postRegionRepository.save(postRegion);
        }

        //일자 카드 저장
        for (int i=0;i<postFormDto.getPostDateCard().size();i++){
            PostDateCard postDateCard = new PostDateCard();
            postDateCard.setPost(post);
            postDateCard.setPostDatecardTitle(postFormDto.getPostDateCard().get(i).getPostDateCardTitle());
            postDateCardRepository.save(postDateCard);
            //일정 카드 저장
            for (int j=0;j<postFormDto.getPostDateCard().get(i).getPostScheduleCard().size();j++){
                PostScheduleCard postScheduleCard = new PostScheduleCard();
                postScheduleCard.setPostDatecard(postDateCard);
                postScheduleCard.setPostSchedulecardPlaceName(postFormDto.getPostDateCard().get(i).getPostScheduleCard().get(j).getPlaceName());
                postScheduleCard.setPostSchedulecardPlaceAddress(postFormDto.getPostDateCard().get(i).getPostScheduleCard().get(j).getPlaceAddress());
                postScheduleCard.setPostSchedulecardContent(postFormDto.getPostDateCard().get(i).getPostScheduleCard().get(j).getPlaceContent());
                postScheduleCardRepository.save(postScheduleCard);
            }
        }
        return postNo;
    }

    @Transactional
    public int postedit(int postNo, PostFormDto postFormDto) {
        //해당 글 찾기
        Post post = postRepository.getReferenceById(postNo);

        //글 업데이트
        post.updatePost(postFormDto);

        int postNoResult = post.getPostNo();
        //해당 글 지역 찾아 삭제
        postRegionRepository.deleteAllByPost(post);
        //지역 업데이트
        for (int i = 0; i < postFormDto.getPostRegionName().size(); i++) {
            PostRegion postRegionUpdate = new PostRegion();
            postRegionUpdate.setPost(post);
            Region region = regionRepository.getByRegionName(postFormDto.getPostRegionName().get(i));
            postRegionUpdate.setRegion(region);
            postRegionRepository.save(postRegionUpdate);
        }

        //해달 글 일자, 일정 카드 찾아서 삭제
        postDateCardRepository.deleteAllByPost(post);
        //일자 카드 업데이트
        for (int i=0;i<postFormDto.getPostDateCard().size();i++){
            PostDateCard postDateCard = new PostDateCard();
            postDateCard.setPost(post);
            postDateCard.setPostDatecardTitle(postFormDto.getPostDateCard().get(i).getPostDateCardTitle());
            postDateCardRepository.save(postDateCard);
            //일정 카드 업데이트
            for (int j=0;j<postFormDto.getPostDateCard().get(i).getPostScheduleCard().size();j++){
                PostScheduleCard postScheduleCard = new PostScheduleCard();
                postScheduleCard.setPostDatecard(postDateCard);
                postScheduleCard.setPostSchedulecardPlaceName(postFormDto.getPostDateCard().get(i).getPostScheduleCard().get(j).getPlaceName());
                postScheduleCard.setPostSchedulecardPlaceAddress(postFormDto.getPostDateCard().get(i).getPostScheduleCard().get(j).getPlaceAddress());
                postScheduleCard.setPostSchedulecardContent(postFormDto.getPostDateCard().get(i).getPostScheduleCard().get(j).getPlaceContent());
                postScheduleCardRepository.save(postScheduleCard);
            }
        }

        return postNoResult;
    }

//    public PostFormDto postdetail(int postNo) {
//        PostFormDto postFormDto = new PostFormDto();
//        Post post = postRepository.getReferenceById(postNo);
//        postFormDto.setPostNo(postNo);
//        postFormDto.setMemberNickName(post.getMember().getMemberNickname());
//        postFormDto.setPostTitle(post.getPostTitle());
//        postFormDto.set
//    }

    public List<MainPostDto> postTopList() {

        List<MainPostDto> mainPostDtoList = new ArrayList<>();
        for (Post post : postRepository.findTopList()) {
            MainPostDto mainPostDto = new MainPostDto();
            mainPostDto.setPostNo(post.getPostNo());
            mainPostDto.setPostTitle(post.getPostTitle());
            mainPostDto.setCreatedTime(post.getCreatedTime());
            mainPostDto.setMemberId(post.getMember().getMemberId());
            mainPostDto.setMemberNickname(post.getMember().getMemberNickname());
            mainPostDto.setPostHeartCnt(post.getPostHeartCnt());

            String filePath = null;
            if (uploadFileRepository.findByPostNo(post.getPostNo()).size() != 0) {
                filePath = uploadFileRepository.findByPostNo(post.getPostNo()).get(0).getFilePath();
            }

            mainPostDto.setFilePath( filePath);

            mainPostDtoList.add(mainPostDto);
        }

        return mainPostDtoList;
    }
}
