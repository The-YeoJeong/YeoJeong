package com.project.yeojeong.post;

import com.project.yeojeong.dto.PostDto;
import com.project.yeojeong.service.MemberService;
import com.project.yeojeong.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@SpringBootTest
@Transactional
public class myPageTest {
    @Autowired
    MemberService memberService;

    @Autowired
    PostService postService;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Test
    @DisplayName("My page test")
    public void myPageTest() {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken("covy_test", "1018");

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        int section = 2;
        String searchContent = "1";
        boolean onlyPlan = false;
        Pageable pageable = PageRequest.of(0, 3);

        ArrayList list = (ArrayList) postService.myPostList(section, searchContent, onlyPlan, pageable, SecurityContextHolder.getContext().getAuthentication()).get("postList");
        for (Object data : list) {
            PostDto postDto = (PostDto) data;
            System.out.println(postDto.toString());
        }
    }
}
