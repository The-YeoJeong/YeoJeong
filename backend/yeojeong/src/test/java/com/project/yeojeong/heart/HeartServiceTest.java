package com.project.yeojeong.heart;

import com.project.yeojeong.entity.Heart;
import com.project.yeojeong.entity.Member;
import com.project.yeojeong.entity.Post;
import com.project.yeojeong.repository.HeartRepository;
import com.project.yeojeong.repository.MemberRepository;
import com.project.yeojeong.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class HeartServiceTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    HeartRepository heartRepository;

    @Test
    @DisplayName("좋아요 등록 테스트")
    public void createHeartTest() {
        Member member = memberRepository.getReferenceById(44);
        Post post = postRepository.getReferenceById(18);

        String result = "";
        if (heartRepository.getByPostAndMember(post, member)!=null){
            result="이미 좋아요를 클릭하셨습니다.";
        } else {
            Heart heart = Heart.createHeart(post, member);
            heartRepository.save(heart);
            post.setPostHeartCnt(post.getPostHeartCnt()+1);
            result="성공";
        }
        System.out.println("좋아요 등록 결과 : "+result);
//
    }

    @Test
    @DisplayName("좋아요 삭제 테스트")
    public void deleteHeartTest() {
        Member member = memberRepository.getReferenceById(44);
        Post post = postRepository.getReferenceById(15);

        Heart heart = Heart.createHeart(post, member);
        heartRepository.save(heart);

        //삭제되었나?
        try {
            heartRepository.deleteById(heart.getHeartNo());
            post.setPostHeartCnt(post.getPostHeartCnt()-1);
            System.out.println("삭제되었습니다.");
        } catch (Exception e){
            System.out.println(e);
        }
    }
}