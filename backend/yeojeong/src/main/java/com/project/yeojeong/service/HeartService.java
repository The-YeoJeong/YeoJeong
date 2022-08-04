package com.project.yeojeong.service;

import com.project.yeojeong.dto.HeartFormDto;
import com.project.yeojeong.entity.Heart;
import com.project.yeojeong.entity.Member;
import com.project.yeojeong.entity.Post;
import com.project.yeojeong.repository.HeartRepository;
import com.project.yeojeong.repository.MemberRepository;
import com.project.yeojeong.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@Transactional
@RequiredArgsConstructor
public class HeartService {
    private  final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final HeartRepository heartRepository;
    public String heartNew(HeartFormDto heartFormDto, Principal principal) {
        Post post = postRepository.getReferenceById(heartFormDto.getPostNo());
        Member member = memberRepository.getByMemberId(principal.getName());
        System.out.println("@@@@@@@@@@@@@@@@@@@#####" + member.getMemberId());
        String result = "";
        if (heartRepository.getByPostAndMember(post, member)!=null){
            result="이미 좋아요를 클릭하셨습니다.";
        } else {
            System.out.println("@@@@@@@@@@@@@@@@@@@#####1" + member.getMemberId());
            Heart heart = Heart.createHeart(post, member);
            System.out.println("@@@@@@@@@@@@@@@@@@@#####1" + member.getMemberId());
            heartRepository.save(heart);

            post.setPostHeartCnt(post.getPostHeartCnt()+1);
            result="성공";
        }
        return result;
    }

    public void heartDelete(HeartFormDto heartFormDto, Principal principal) {
        Post post = postRepository.getReferenceById(heartFormDto.getPostNo());
        Member member = memberRepository.getByMemberId(principal.getName());
        Heart heart = heartRepository.getByPostAndMember(post, member);
        heartRepository.deleteById(heart.getHeartNo());
        post.setPostHeartCnt(post.getPostHeartCnt()-1);
    }
}
