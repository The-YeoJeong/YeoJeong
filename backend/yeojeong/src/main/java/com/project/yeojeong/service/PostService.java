package com.project.yeojeong.service;

import com.project.yeojeong.dto.ConditionDto;
<<<<<<< HEAD
import com.project.yeojeong.dto.PostDto;
=======
import com.project.yeojeong.dto.MainPostDto;
import com.project.yeojeong.dto.PostDateCardDto;
>>>>>>> 4ecbffc39a35755e2b0a42232d2283904b568516
import com.project.yeojeong.dto.PostFormDto;
import com.project.yeojeong.dto.PostScheduleCardDto;
import com.project.yeojeong.entity.*;
import com.project.yeojeong.repository.*;
import com.project.yeojeong.specification.PostSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
<<<<<<< HEAD
import java.util.Map;
=======

>>>>>>> 4ecbffc39a35755e2b0a42232d2283904b568516

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
    private final HeartRepository heartRepository;

    //글 작성
    public int postnew(PostFormDto postFormDto, Principal principal) {
        Member member = memberRepository.getByMemberId(principal.getName());
        //전체 글 저장
        Post post = Post.createPost(postFormDto, member);
        int postNo = postRepository.save(post).getPostNo();
        //글 해당 지역 저장
        for (int i = 0; i < postFormDto.getPostRegionName().size(); i++) {
            PostRegion postRegion = new PostRegion();
            postRegion.setPost(post);
            Region region = regionRepository.getByRegionName(postFormDto.getPostRegionName().get(i));
            postRegion.setRegion(region);
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

        return postNoResult;
    }

    public PostFormDto postDetail(int postNo, Principal principal) {
        Post post = postRepository.getReferenceById(postNo);
        List<PostRegion> postRegion = postRegionRepository.getAllByPost(post);
        List<PostDateCard> postDateCard = postDateCardRepository.getAllByPost(post);
        List<String> regionName = new ArrayList<>();

        PostFormDto postFormDto = new PostFormDto();
        postFormDto.setPostNo(postNo);
        postFormDto.setPostTitle(post.getPostTitle());
        postFormDto.setMemberId(post.getMember().getMemberId());
        postFormDto.setMemberNickname(post.getMember().getMemberNickname());
        postFormDto.setCreatedTime(post.getCreatedTime());
        postFormDto.setHeartCnt(post.getPostHeartCnt());
        postFormDto.setPostStartDate(post.getPostStartdate());
        postFormDto.setPostEndDate(post.getPostEnddate());
        postFormDto.setPostOnlyMe(post.isPostOnlyme());
        postFormDto.setPostContent(post.getPostContent());

        //해당 글 지역 가져오기
        for (int i=0;i<postRegion.size();i++) {
            regionName.add(postRegion.get(i).getRegion().getRegionName());
        }
        postFormDto.setPostRegionName(regionName);

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
        postFormDto.setPostDateCard(postDateCardDtoList);

        //하트 확인
        //로그인 됐을 때
        if (principal==null) {
            postFormDto.setLiked(false);
        }
        //로그인 안 했을 때
        else {
            Member member = memberRepository.getByMemberId(principal.getName());
            Heart heart = heartRepository.getByPostAndMember(post,member);
            if (heart!=null) {
                postFormDto.setLiked(true);
            } else {
                postFormDto.setLiked(false);
            }
        }

        return postFormDto;
    }

    // 메인 TOP3
    public List<PostDto> postTopList() {
        List<PostDto> PostDtoList = new ArrayList<>();
        for (Post post : postRepository.findTopList()) {
            String filePath = null;
            if (uploadFileRepository.findByPostNo(post.getPostNo()).size() != 0) {
                filePath = uploadFileRepository.findByPostNo(post.getPostNo()).get(0).getFilePath();
            }

            PostDto PostDto = Post.createPostDto(post, filePath);
            PostDtoList.add(PostDto);
        }

        return PostDtoList;
    }

    // 메인페이지
    public Map<String, Object> postList(ConditionDto conditionDto, Pageable pageable) {
        Specification<Post> spec = (root, query, criteriaBuilder) -> null;
        // 전체 공개 게시글만 노출
        spec = spec.and(PostSpecification.fullDisclosure());
        if (conditionDto.getRegionName() != null)
            spec = spec.and(PostSpecification.findRegionName(conditionDto.getRegionName()));
        //후기가 포함된 글만 노출
        if (conditionDto.isPostContent())
            spec = spec.and(PostSpecification.onlyContent());
        // conditionDto.getPeriod()에 값이 없을경우 int 형이라 default 0으로 잡혀 조건에 걸림 -> Integer 형으로 변환
        if (conditionDto.getPeriod() != null && conditionDto.getPeriod() >= 0 && conditionDto.getPeriod() < 5)
            spec = spec.and(PostSpecification.period(conditionDto.getPeriod()));
        // if - else if문으로 true / false 처리하는 것보다 if - else 문으로 null 값일 경우에 else 조건문에 걸리도록
        if (conditionDto.isOrder()) {
            spec = spec.and(PostSpecification.orderHeart());
        } else {
            spec = spec.and(PostSpecification.orderDate());
        }
        // 제목 필드와 후기(내용) 컬럼, 두개를 검색해야하기 때문에 spec에 두개를 and, or 조건으로 걸수 없음
        // and title, and content로 걸시, 두 컬럼에 동시에 포함되는 row return
        // or title, or content로 조건 설정시, 다른 조건 + 제목, 내용 검색으로 제목, 내용에 검색되는 row가 다른조건에 부합하지 않더라도 return됨
        // 하나의 and조건으로 걸고 searchTitleAndContent 내부에서 or 조건으로 return
        if (conditionDto.getSearchContent() != null) {
            spec = spec.and(PostSpecification.searchTitleAndContent(conditionDto.getSearchContent()));
        }

        List<PostDto> PostDtoList = new ArrayList<>();
        for (Post post : postRepository.findAll(spec, pageable)) {
            String filePath = null;
            if (uploadFileRepository.findByPostNo(post.getPostNo()).size() != 0) {
                filePath = uploadFileRepository.findByPostNo(post.getPostNo()).get(0).getFilePath();
            }

            PostDto PostDto = Post.createPostDto(post, filePath);
            PostDtoList.add(PostDto);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("postList", PostDtoList);
        map.put("postCnt", postRepository.findAll(spec, pageable).getTotalElements());

        return map;
    }

    // 마이페이지
    public Map<String, Object> myPostList(int section, String searchContent, boolean onlyPlan, Pageable pageable, Principal principal) {
        Specification<Post> spec = (root, query, criteriaBuilder) -> null;
        switch (section) {
            case 0:
                // 내 게시글
                spec = spec.and(PostSpecification.myPost(principal.getName()));
                if (searchContent != null)
                    // 검색
                    spec = spec.and(PostSpecification.searchTitleAndContent(searchContent));
                if (onlyPlan)
                    // 계획만 보기(false나 null은 전체보기)
                    spec = spec.and(PostSpecification.onlyPlan());
                break;
            case 1:
                // 좋아요 누른 게시글
                spec = spec.and(PostSpecification.heartedPost(principal.getName()));
                break;
            case 2:
                // 댓글 단 게시글
                spec = spec.and(PostSpecification.commentedPost(principal.getName()));
                break;
        }
        // 최근날짜 정렬
        spec = spec.and(PostSpecification.orderDate());

        List<PostDto> PostDtoList = new ArrayList<>();
        for (Post post : postRepository.findAll(spec, pageable)) {
            String filePath = null;
            if (uploadFileRepository.findByPostNo(post.getPostNo()).size() != 0) {
                filePath = uploadFileRepository.findByPostNo(post.getPostNo()).get(0).getFilePath();
            }

            PostDto PostDto = Post.createPostDto(post, filePath);
            PostDtoList.add(PostDto);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("postList", PostDtoList);
        map.put("postCnt", postRepository.findAll(spec, pageable).getTotalElements());

        return map;
    }
}
