package com.project.yeojeong.post;

import com.project.yeojeong.dto.*;
import com.project.yeojeong.entity.*;
import com.project.yeojeong.repository.*;
import com.project.yeojeong.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
public class PostServiceTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    RegionRepository regionRepository;
    @Autowired
    PostRegionRepository postRegionRepository;
    @Autowired
    PostDateCardRepository postDateCardRepository;
    @Autowired
    PostScheduleCardRepository postScheduleCardRepository;
    @Autowired
    HeartRepository heartRepository;

    public PostFormDto createPostFormDto() {
        Date date = new Date();
        PostFormDto postFormDto = new PostFormDto();
        postFormDto.setPostNo(1);
        postFormDto.setPostTitle("제목");
        postFormDto.setPostStartDate(date);
        postFormDto.setPostEndDate(date);
        postFormDto.setPostContent("내용");
        postFormDto.setPostOnlyMe(true);

        List<String> region = new ArrayList<>();
        region.add("서울");
        region.add("경기");
        postFormDto.setPostRegionName(region);

        List<PostDateCardDto> postDateCardDtoList = new ArrayList<>();
        PostDateCardDto postDateCardDto = new PostDateCardDto();
        postDateCardDto.setPostDateCardTitle("일자 카드 제목");

        List<PostScheduleCardDto> postScheduleCardDtoList = new ArrayList<>();
        PostScheduleCardDto postScheduleCardDto = new PostScheduleCardDto();
        postScheduleCardDto.setPlaceName("대전 성심당");
        postScheduleCardDto.setPlaceAddress("대전 주소");
        postScheduleCardDto.setPlaceContent("일정 카드 내용");
        postScheduleCardDtoList.add(postScheduleCardDto);
        postDateCardDto.setPostScheduleCard(postScheduleCardDtoList);

        postDateCardDtoList.add(postDateCardDto);
        postFormDto.setPostDateCard(postDateCardDtoList);

        return postFormDto;
    }

    @Test
    @DisplayName("글 등록 테스트")
    public void createPostTest() {
        Member member = memberRepository.getReferenceById(44);
        PostFormDto postFormDto = createPostFormDto();

        Post post = Post.createPost(postFormDto, member);
        postRepository.save(post);

        //글 해당 지역 저장
        for (int i = 0; i < postFormDto.getPostRegionName().size(); i++) {
            PostRegion postRegion = new PostRegion();
            postRegion.setPost(post);
            Region region2 = regionRepository.getByRegionName(postFormDto.getPostRegionName().get(i));
            postRegion.setRegion(region2);
            postRegionRepository.save(postRegion);
        }

        //일자 카드 저장
        for (int i = 0; i < postFormDto.getPostDateCard().size(); i++) {
            PostDateCard postDateCard = new PostDateCard();
            postDateCard.setPost(post);
            postDateCard.setPostDatecardTitle(postFormDto.getPostDateCard().get(i).getPostDateCardTitle());
            postDateCardRepository.save(postDateCard);
            //일정 카드 저장
            for (int j = 0; j < postFormDto.getPostDateCard().get(i).getPostScheduleCard().size(); j++) {
                PostScheduleCard postScheduleCard = new PostScheduleCard();
                postScheduleCard.setPostDatecard(postDateCard);
                postScheduleCard.setPostSchedulecardPlaceName(postFormDto.getPostDateCard().get(i).getPostScheduleCard().get(j).getPlaceName());
                postScheduleCard.setPostSchedulecardPlaceAddress(postFormDto.getPostDateCard().get(i).getPostScheduleCard().get(j).getPlaceAddress());
                postScheduleCard.setPostSchedulecardContent(postFormDto.getPostDateCard().get(i).getPostScheduleCard().get(j).getPlaceContent());
                postScheduleCardRepository.save(postScheduleCard);
            }
        }
        assertEquals(postFormDto.getPostTitle(),post.getPostTitle());
        assertEquals(postFormDto.getPostContent(),post.getPostContent());
        assertEquals(postFormDto.getPostDateCard().get(0).getPostDateCardTitle(),"일자 카드 제목");
        assertEquals(postFormDto.getPostDateCard().get(0).getPostScheduleCard().get(0).getPlaceName(),"대전 성심당");
    }

    @Test
    @DisplayName("글 수정 테스트")
    public void updatePostTest(){
        //해당 글 찾기
        Post post = postRepository.getReferenceById(15);
        PostFormDto postFormDto = createPostFormDto();

        //글 업데이트
        post.updatePost(postFormDto);

        //해당 글 지역 찾아 삭제
        postRegionRepository.deleteAllByPost(post);
        //지역 업데이트
        for (int i = 0; i < postFormDto.getPostRegionName().size(); i++) {
            PostRegion postRegionUpdate = new PostRegion();
            postRegionUpdate.setPost(post);
            Region regionResult = regionRepository.getByRegionName(postFormDto.getPostRegionName().get(i));
            postRegionUpdate.setRegion(regionResult);
            postRegionRepository.save(postRegionUpdate);
        }

        //해달 글 일자, 일정 카드 찾아서 삭제
        postDateCardRepository.deleteAllByPost(post);
        //일자 카드 업데이트
        for (int i = 0; i < postFormDto.getPostDateCard().size(); i++) {
            PostDateCard postDateCard = new PostDateCard();
            postDateCard.setPost(post);
            postDateCard.setPostDatecardTitle(postFormDto.getPostDateCard().get(i).getPostDateCardTitle());
            postDateCardRepository.save(postDateCard);
            //일정 카드 업데이트
            for (int j = 0; j < postFormDto.getPostDateCard().get(i).getPostScheduleCard().size(); j++) {
                PostScheduleCard postScheduleCard = new PostScheduleCard();
                postScheduleCard.setPostDatecard(postDateCard);
                postScheduleCard.setPostSchedulecardPlaceName(postFormDto.getPostDateCard().get(i).getPostScheduleCard().get(j).getPlaceName());
                postScheduleCard.setPostSchedulecardPlaceAddress(postFormDto.getPostDateCard().get(i).getPostScheduleCard().get(j).getPlaceAddress());
                postScheduleCard.setPostSchedulecardContent(postFormDto.getPostDateCard().get(i).getPostScheduleCard().get(j).getPlaceContent());
                postScheduleCardRepository.save(postScheduleCard);
            }
        }
        assertEquals(postFormDto.getPostTitle(),post.getPostTitle());
        assertEquals(postFormDto.getPostContent(),post.getPostContent());
        assertEquals(postFormDto.getPostDateCard().get(0).getPostDateCardTitle(),"일자 카드 제목");
        assertEquals(postFormDto.getPostDateCard().get(0).getPostScheduleCard().get(0).getPlaceName(),"대전 성심당");
    }

    @Test
    @DisplayName("글 삭제 테스트")
    public void deletePost(){
        Member member = memberRepository.getReferenceById(44);

        PostFormDto postFormDto = createPostFormDto();
        Post post = Post.createPost(postFormDto, member);
        postRepository.save(post);

        //삭제되었나?
        try {
            postRepository.deleteById(post.getPostNo());
            System.out.println("삭제되었습니다.");
        } catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    @DisplayName("글 디테일 테스트")
    public void detailPostTest(){
        //글 등록
        Member member = memberRepository.getReferenceById(44);
        PostFormDto postFormDto = createPostFormDto();
        Post post = Post.createPost(postFormDto,member);
        postRepository.save(post);

        List<PostRegion> postRegion = postRegionRepository.getAllByPost(post);
        List<PostDateCard> postDateCard = postDateCardRepository.getAllByPost(post);
        List<String> regionName = new ArrayList<>();

        PostFormDto postFormDtoDetail = new PostFormDto();
        postFormDtoDetail.setPostNo(15);
        postFormDtoDetail.setPostTitle(post.getPostTitle());
        postFormDtoDetail.setMemberId(post.getMember().getMemberId());
        postFormDtoDetail.setMemberNickname(post.getMember().getMemberNickname());
        postFormDtoDetail.setCreatedTime(post.getCreatedTime());
        postFormDtoDetail.setHeartCnt(post.getPostHeartCnt());
        postFormDtoDetail.setPostStartDate(post.getPostStartdate());
        postFormDtoDetail.setPostEndDate(post.getPostEnddate());
        postFormDtoDetail.setPostOnlyMe(post.isPostOnlyme());
        postFormDtoDetail.setPostContent(post.getPostContent());

        //해당 글 지역 가져오기
        for (int i=0;i<postRegion.size();i++) {
            regionName.add(postRegion.get(i).getRegion().getRegionName());
        }
        postFormDtoDetail.setPostRegionName(regionName);

        //해당 글 일자 카드 가져오기
        List<PostDateCardDto> postDateCardDtoList = new ArrayList<>();
        for (int i=0;i<postDateCard.size();i++){
            PostDateCardDto postDateCardDto = new PostDateCardDto();
            postDateCardDto.setPostDatecardNo(postDateCard.get(i).getPostDatecardNo());
            postDateCardDto.setPostDateCardTitle(postDateCard.get(i).getPostDatecardTitle());

            //해당 일정 카드 가져오기
            List<PostScheduleCard> postScheduleCard = postScheduleCardRepository.getAllByPostDatecard(postDateCard.get(i));
            List<PostScheduleCardDto> postScheduleCardDtoList = new ArrayList<>();
            for (int j=0;j<postScheduleCard.size();j++){
                PostScheduleCardDto postScheduleCardDto = new PostScheduleCardDto();
                postScheduleCardDto.setPostSchedulecardNo(postScheduleCard.get(i).getPostSchedulecardNo());
                postScheduleCardDto.setPlaceName(postScheduleCard.get(i).getPostSchedulecardPlaceName());
                postScheduleCardDto.setPlaceAddress(postScheduleCard.get(i).getPostSchedulecardPlaceAddress());
                postScheduleCardDto.setPlaceContent(postScheduleCard.get(i).getPostSchedulecardContent());
                postScheduleCardDtoList.add(postScheduleCardDto);
            }

            postDateCardDto.setPostScheduleCard(postScheduleCardDtoList);
            postDateCardDtoList.add(postDateCardDto);
        }
        postFormDtoDetail.setPostDateCard(postDateCardDtoList);

        //하트 확인
        Heart heart = heartRepository.getByPostAndMember(post,member);
        if (heart!=null) {
            postFormDto.setLiked(true);
        } else {
            postFormDto.setLiked(false);
        }

        System.out.println(postFormDto.getPostNo());
        System.out.println(postFormDto.getPostTitle());
        System.out.println(postFormDto.getMemberNickname());
        System.out.println(postFormDto.getPostStartDate()+"~"+postFormDto.getPostEndDate());
        System.out.println(postFormDto.getPostRegionName().get(0));
        System.out.println(postFormDto.getPostDateCard().get(0).getPostDateCardTitle());
        System.out.println(postFormDto.getPostDateCard().get(0).getPostScheduleCard().get(0).getPlaceName());
        System.out.println(postFormDto.isLiked());
    }
}