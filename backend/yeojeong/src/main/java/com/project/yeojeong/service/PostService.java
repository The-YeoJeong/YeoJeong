package com.project.yeojeong.service;

import com.project.yeojeong.dto.PostFormDto;
import com.project.yeojeong.entity.Member;
import com.project.yeojeong.entity.Post;
import com.project.yeojeong.entity.PostDateCard;
import com.project.yeojeong.repository.MemberRepository;
import com.project.yeojeong.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    public void postnew(PostFormDto postFormDto) {
        Member member = memberRepository.getById(postFormDto.getMemberNo());
        Post post = Post.createPost(postFormDto, member);
        PostDateCard postDateCard = PostDateCard.createPostDateCard(postFormDto, post);
    }
}
