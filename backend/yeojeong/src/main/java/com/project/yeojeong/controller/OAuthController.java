package com.project.yeojeong.controller;


import com.project.yeojeong.dto.MemberDto;
import com.project.yeojeong.entity.Member;
import com.project.yeojeong.jwt.TokenProvider;
import com.project.yeojeong.service.MemberService;
import com.project.yeojeong.service.OAuthService;
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

    public OAuthController(OAuthService oAuthService, MemberService memberService , TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.oAuthService = oAuthService;
        this.memberService = memberService;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @GetMapping("/kakao")
    public String kakaoLoginConnentURL() {
        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id=d173137e871d96ad298bf43551057b2a");
        url.append("&redirect_uri=http://localhost/oauth2/kakao/login");
        url.append("&response_type=code");

        return "redirect:" + url;
    }

    // redirect url로부터 직접 받음
    @GetMapping("/kakao/login")
    @ResponseBody
    public ResponseEntity oAuthLoginWithCode(@RequestParam String code) {
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

        Map<String, Object> body = new HashMap<>();
        body.put("accessToken", accessToken);

        // 사용자 없음(추가 회원가입 진행)
        // 클라이언트한테 다시 access token을 던짐
        return new ResponseEntity(body, HttpStatus.ACCEPTED);
    }

    // 추가 회원가입: access token, id, nick 받아서 회원가입 처리
    @PostMapping("/{oauth_type}/new")
    @ResponseBody
    public ResponseEntity oAuthSignUp(@PathVariable("oauth_type") String oauth_type,
                                      @RequestParam String accessToken, @RequestBody @Valid MemberDto memberDto) {
        String id = "";
        if(oauth_type.equals("kakao")){
            // 고유식별값을 받아옴
            id = oAuthService.createKakaoUser(accessToken);
        } else if (oauth_type.equals("naver")) {
            id = oAuthService.createNaverUser(accessToken);
        }

        // 사용자 디비 조회, 있을 때
        if (memberService.oAuthDuplicateCheck(id) != null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> body = new HashMap<>();
        body.put("jwt", tokenProvider.createToken(memberService.signup(memberDto, id).getMemberId()));

        return new ResponseEntity(body, HttpStatus.OK);
    }


    @GetMapping("/naver")
    public String naverLoginConnentURL() {
        // state용 난수 생성
        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString(32);

        StringBuffer url = new StringBuffer();
        url.append("https://nid.naver.com/oauth2.0/authorize?");
        url.append("client_id=KWUoslendQDxZRSrkOFh");
        url.append("&response_type=code");
        url.append("&redirect_uri=http://localhost/oauth2/naver/login");
        url.append("&state=" + state);

        return "redirect:" + url;
    }

    // redirect url로부터 직접 받음
    @GetMapping("/naver/login")
    @ResponseBody
    public ResponseEntity naverLoginWithCode(@RequestParam(value = "code") String code,
                                   @RequestParam(value = "state") String state) {
        String accessToken = oAuthService.getNaverAccessToken(code, state);
        String id = oAuthService.createNaverUser(accessToken);

        // 사용자 디비 조회
        Member member =  memberService.oAuthDuplicateCheck(id);

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


}
