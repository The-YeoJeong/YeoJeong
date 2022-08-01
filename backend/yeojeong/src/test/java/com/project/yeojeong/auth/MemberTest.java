package com.project.yeojeong.auth;

import com.project.yeojeong.dto.MemberDto;
import com.project.yeojeong.entity.Member;
import com.project.yeojeong.repository.MemberRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @PersistenceContext
    EntityManager em;

    public MemberDto createMember(String id, String pw, String nick) {
        MemberDto memberDto = new MemberDto();
        memberDto.setMemberId(id);
        memberDto.setMemberPw(pw);
        memberDto.setMemberNickname(nick);

        return memberService.signup(memberDto);
    }

    @Test
    @DisplayName("회원 가입 테스트")
    public void signUpTest() throws Exception {
        createMember("test", "test", "test");
    }

    @Test
    @DisplayName("id 중복 확인 테스트")
    public void idDuplicationTest() throws Exception {
        // 이미 존재하는 아이디 - not null 성공
        try {
            createMember("test", "test", "test");
            assertNotNull(memberService.memberIdDuplicateCheck("test"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("nick 중복 확인 테스트")
    public void nickDuplicationTest() throws Exception {
        // 이미 존재하는 아이디 - not null 성공
        try {
            createMember("test", "test", "test");
            assertNotNull(memberService.memberNickNameDuplicateCheck("test"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("로그인 테스트")
    public void loginTest() throws Exception {
        createMember("test", "test1!", "test");

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken("test", "test1!");

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertEquals(SecurityContextHolder.getContext().getAuthentication().getName(), "test");
    }

    @Test
    @DisplayName("유저정보 반환 테스트")
    public void getUserSelfTest() throws Exception {
        createMember("test", "test1!", "test");

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken("test", "test1!");

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertEquals(memberService.getMyUserWithAuthorities().getMemberId(), "test") ;
    }

    @Test
    @DisplayName("Auditing 테스트")
    @WithMockUser(username = "test")
    public void auditingTest() {
        Member member = memberRepository.getByMemberId(createMember("test", "test1!", "test").getMemberId());

        System.out.println("created time : " + member.getCreatedTime());
        System.out.println("updated time : " + member.getUpdatedTime());
        System.out.println("created member : " + member.getCreatedBy());
        System.out.println("updated member : " + member.getUpdatedBy());
    }

    @Test
    @DisplayName("회원 탈퇴 테스트")
    public void memberDeleteTest() throws Exception {
        MemberDto signupMember = createMember("test", "test1!", "test");

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken("test", "test1!");

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        memberRepository.deleteById(memberRepository.getByMemberId(signupMember.getMemberId()).getMemberNo());
    }
}