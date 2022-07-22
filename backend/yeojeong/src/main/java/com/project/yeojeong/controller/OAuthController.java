package com.project.yeojeong.controller;


import com.project.yeojeong.dto.MemberDto;
import com.project.yeojeong.entity.Member;
import com.project.yeojeong.jwt.TokenProvider;
import com.project.yeojeong.service.MemberService;
import com.project.yeojeong.service.OAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
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


    // 인가 코드 받기(원래는 프론트단에서 처리, TEST 용) 추후 삭제 예정
    // https://kauth.kakao.com/oauth/authorize?client_id=d173137e871d96ad298bf43551057b2a&redirect_uri=http://localhost/oauth2/kakao&response_type=code
    @GetMapping("/kakao")
    public ResponseEntity kakaoLoginWithCode(@RequestParam String code) {
        // 아래 두줄로 사용자 고유식별값을 받아옴
        String accessToken = oAuthService.getKakaoAccessToken(code);
        String id = oAuthService.createKakaoUser(accessToken);

        // 사용자 디비 조회
        Member member =  memberService.oAuthDuplicateCheck(id);

        // 사용자 있음
        if (member != null) {
            Map<String, Object> body = new HashMap<>();
            body.put("jwt", tokenProvider.createToken(member.getMemberId()));

            return new ResponseEntity(body, HttpStatus.OK);
        }

        // 사용자 없음(추가 회원가입 진행)
        // 클라이언트한테 다시 access token을 던져야하는지 여부 check
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    // 소셜 최초 로그인인지, 가입한 유저인지 판단 with access token
    @GetMapping("/kakao/login")
    public ResponseEntity kakaoLogin(@RequestParam String accessToken) {
        // 고유식별값을 받아옴
        String id = oAuthService.createKakaoUser(accessToken);

        // 사용자 디비 조회
        Member member =  memberService.oAuthDuplicateCheck(id);

        // 사용자 있음
        if (member != null) {
            Map<String, Object> body = new HashMap<>();
            body.put("jwt", tokenProvider.createToken(member.getMemberId()));

            return new ResponseEntity(body, HttpStatus.OK);
        }

        // 사용자 없음(추가 회원가입 진행)
        // 클라이언트한테 다시 access token을 던져야하는지 여부 check
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    // 추가 회원가입: access token, id, nick 받아서 회원가입 처리
    @PostMapping("/kakao/new")
    public ResponseEntity kakaoSignUp(@RequestParam String accessToken, @RequestBody @Valid MemberDto memberDto) {
        // 고유식별값을 받아옴
        String id = oAuthService.createKakaoUser(accessToken);

        // 사용자 디비 조회, 있을 때
        if (memberService.oAuthDuplicateCheck(id) != null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> body = new HashMap<>();
        body.put("jwt", tokenProvider.createToken(memberService.signup(memberDto, id).getMemberId()));

        return new ResponseEntity(body, HttpStatus.OK);
    }
}
