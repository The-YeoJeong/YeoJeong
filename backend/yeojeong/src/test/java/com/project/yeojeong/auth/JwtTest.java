package com.project.yeojeong.auth;

import com.project.yeojeong.dto.MemberDto;
import com.project.yeojeong.jwt.TokenProvider;
import com.project.yeojeong.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class JwtTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private TokenProvider tokenProvider;


    public MemberDto createMember(String id, String pw, String nick) {
        MemberDto memberDto = new MemberDto();
        memberDto.setMemberId(id);
        memberDto.setMemberPw(pw);
        memberDto.setMemberNickname(nick);

        return memberService.signup(memberDto);
    }

    @Test
    @DisplayName("JWT 발급")
    public void jwtCreation() {
        createMember("test", "test1!", "test");

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken("test", "test1!");

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        System.out.println(jwt);
    }
}
