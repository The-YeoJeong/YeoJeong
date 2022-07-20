package com.project.yeojeong.service;

import com.project.yeojeong.dto.PostFormDto;
import com.project.yeojeong.entity.Member;
import com.project.yeojeong.entity.Post;
import com.project.yeojeong.entity.PostDateCard;
import com.project.yeojeong.entity.PostScheduleCard;
import com.project.yeojeong.repository.MemberRepository;
import com.project.yeojeong.repository.PostDateCardRepository;
import com.project.yeojeong.repository.PostRepository;
import com.project.yeojeong.repository.PostScheduleCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final PostDateCardRepository postDateCardRepository;
    private final PostScheduleCardRepository postScheduleCardRepository;

    //글 작성
    public int postnew(PostFormDto postFormDto, Principal principal) {
        Member member = memberRepository.getByMemberId(principal.getName());
        //전체 글 저장
        Post post = Post.createPost(postFormDto, member);
        postRepository.save(post);

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
                postScheduleCard.setPostSchedulecardPlaceContent(postFormDto.getPostDateCard().get(i).getPostScheduleCard().get(j).getPlaceContent());
                postScheduleCardRepository.save(postScheduleCard);
            }
        }
        return post.getPostNo();
    }
}
