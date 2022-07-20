package com.project.yeojeong.controller;

import com.project.yeojeong.dto.LoginDto;
import com.project.yeojeong.dto.MemberDto;
import com.project.yeojeong.dto.TokenDto;
import com.project.yeojeong.jwt.JwtFilter;
import com.project.yeojeong.jwt.TokenProvider;
import com.project.yeojeong.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public MemberController(MemberService memberService, TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.memberService = memberService;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    // login해서 token 반환(client로 보냄) 이 token을 다시 서버에 요청과 같이 보내서 서비스 수행
    @PostMapping("/login")
    public ResponseEntity<Map> authorize(@Valid @RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getMemberId(), loginDto.getMemberPw());
        // authenticationToken을 이용해 Authentication 객체를 생성하려고 authenticate method가 실행이 될때
        // CustomUserDetailsService.loadUserByUsername method 실행
        // 실행한 결과값으로 authentication 객체를 생성하고 SecurityContext에 저장
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // authentication 객체를 createToken메소드를 통해서 JWT Token 생성
        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        // jwt를 response header에 넣어줌
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        Map<String, Object> body = new HashMap<>();
        body.put("jwt", jwt);
        body.put("memberNickName", authentication.getName());


        // Map 형태로 jwt, memberNickName response
        return new ResponseEntity<>(body, httpHeaders, HttpStatus.OK);
    }

    // 회원 가입
    @PostMapping("/new")
    public ResponseEntity<MemberDto> signup(@Valid @RequestBody MemberDto memberDto) {
        return ResponseEntity.ok(memberService.signup(memberDto));
    }

    // test용
    // 자신의 정보 가져오기
    @GetMapping("/get/me")
    public ResponseEntity<MemberDto> getMyUserInfo(HttpServletRequest request, Principal principal) {
        return ResponseEntity.ok(memberService.getMyUserWithAuthorities());
    }
}
