package com.project.yeojeong.controller;


import com.project.yeojeong.dto.MemberDto;
import com.project.yeojeong.entity.Member;
import com.project.yeojeong.jwt.TokenProvider;
import com.project.yeojeong.service.MemberService;
import com.project.yeojeong.service.OAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/oauth2")
public class OAuthController {
    private final OAuthService oAuthService;
    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final String KAKAO = "kakao";
    private final String NAVER = "naver";
    private final String GOOGLE = "google";

    @Value("${oauth2.google.client_id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${oauth2.naver.client_id}")
    private String NAVER_CLIENT_ID;

    @Value("${oauth2.kakao.client_id}")
    private String KAKAO_CLIENT_ID;

    public OAuthController(OAuthService oAuthService, MemberService memberService, TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.oAuthService = oAuthService;
        this.memberService = memberService;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    // 최초 로그인 창으로 연결
    @GetMapping("/{oauthType}")
    public ResponseEntity LoginConnectURL(@PathVariable("oauthType") String oauthType) {
        StringBuffer url = new StringBuffer();

        switch (oauthType) {
            case KAKAO:
                url.append("https://kauth.kakao.com/oauth/authorize?");
                url.append("client_id=");
                url.append(KAKAO_CLIENT_ID);
                url.append("&redirect_uri=http://localhost/oauth2/kakao/login");
                url.append("&response_type=code");

                break;
            case NAVER:
                // state용 난수 생성
                SecureRandom random = new SecureRandom();
                String state = new BigInteger(130, random).toString(32);

                url.append("https://nid.naver.com/oauth2.0/authorize?");
                url.append("client_id=");
                url.append(NAVER_CLIENT_ID);
                url.append("&response_type=code");
                url.append("&redirect_uri=http://localhost/oauth2/naver/login");
                url.append("&state=");
                url.append(state);

                break;
            case GOOGLE:
                url.append("https://accounts.google.com/o/oauth2/v2/auth");
                url.append("?client_id=");
                url.append(GOOGLE_CLIENT_ID);
                url.append("&redirect_uri=http://localhost/oauth2/google/login");
                url.append("&response_type=code&scope=email&access_type=offline");

                break;
        }
        return new ResponseEntity(url, HttpStatus.OK);
    }


    // 최초 로그인창 연결후 내부적으로 redirect
    @GetMapping("/{oauthType}/login")
    @ResponseBody
    public ResponseEntity oauthLoginWithCode(@PathVariable("oauthType") String oauthType, @RequestParam String code,
                                             @RequestParam(value = "state", required = false) String state) {
        String accessToken = "";
        String id = "";
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^" + code);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^" + state);
        switch (oauthType) {
            case KAKAO:
                System.out.println("1");
                accessToken = oAuthService.getKakaoAccessToken(code);
                System.out.println("2");
                id = oAuthService.createKakaoUser(accessToken);
                System.out.println("3");
                break;
            case NAVER:
                accessToken = oAuthService.getNaverAccessToken(code, state);
                id = oAuthService.createNaverUser(accessToken);
                break;
            case GOOGLE:
                accessToken = oAuthService.getGoogleAccessToken(code);
                id = oAuthService.createGoogleUser(accessToken);
                break;
            default:
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        // 사용자 디비 조회
        Member member = memberService.oAuthDuplicateCheck(id);

        // 사용자 있음
        if (member != null) {
            Map<String, Object> body = new HashMap<>();
            body.put("jwt", tokenProvider.createToken(member.getMemberId()));

            return new ResponseEntity(body, HttpStatus.OK);
        }

        Map<String, Object> body = new HashMap<>();
        body.put("accessToken", accessToken);

        // 사용자 없음(추가 회원가입 진행)
        // 클라이언트한테 다시 access token을 던짐
        return new ResponseEntity(body, HttpStatus.ACCEPTED);
    }

    // 추가 회원가입: access token, id, nick 받아서 회원가입 처리
    @PostMapping("/{oauthType}/new")
    @ResponseBody
    public ResponseEntity oAuthSignUp(@PathVariable("oauthType") String oauthType,
                                      @RequestParam String accessToken, @RequestBody @Valid MemberDto memberDto) {
        String id = "";
        switch (oauthType) {
            case KAKAO:
                id = oAuthService.createKakaoUser(accessToken);
                break;
            case NAVER:
                id = oAuthService.createNaverUser(accessToken);
                break;
            case GOOGLE:
                id = oAuthService.createGoogleUser(accessToken);
                break;
            default:
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        // 사용자 디비 조회, 있을 때
        if (memberService.oAuthDuplicateCheck(id) != null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            Map<String, Object> body = new HashMap<>();
            body.put("jwt", tokenProvider.createToken(memberService.signup(memberDto, id).getMemberId()));

            return new ResponseEntity(body, HttpStatus.OK);
        }
    }
}
