package com.project.yeojeong.controller;

import com.project.yeojeong.dto.LoginDto;
import com.project.yeojeong.dto.MemberDto;
import com.project.yeojeong.entity.Member;
import com.project.yeojeong.jwt.JwtFilter;
import com.project.yeojeong.jwt.TokenProvider;
import com.project.yeojeong.repository.MemberRepository;
import com.project.yeojeong.service.MemberService;
import com.project.yeojeong.service.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.JaasAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/oauth2")
public class OAuthController {

    private final OAuthService oAuthService;
    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public OAuthController(OAuthService oAuthService, MemberService memberService , TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.oAuthService = oAuthService;
        this.memberService = memberService;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }


    // 인가 코드 받기(원래는 프론트단에서 처리, TEST 용)
    // https://kauth.kakao.com/oauth/authorize?client_id=d173137e871d96ad298bf43551057b2a&redirect_uri=http://localhost/oauth2/code/kakao&response_type=code
    @GetMapping("/code/kakao")
    public ResponseEntity socialLogin(@RequestParam String code) {
        // 아래 두줄로 사용자 고유식별값을 받아옴
        String accessToken = oAuthService.getKakaoAccessToken(code);
        String id = oAuthService.createKakaoUser(accessToken);

        // 사용자 디비 조회
        Member member =  memberService.oAuthDuplicateCheckService(id);

        // 사용자 있음
        if (member != null) {
            String jwt = tokenProvider.createToken(member.getMemberId());
            return new ResponseEntity(jwt, HttpStatus.OK);
        }
        // 사용자 없음(추가 회원가입 진행)
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
