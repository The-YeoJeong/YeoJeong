package com.project.yeojeong.service;

import com.project.yeojeong.dto.MemberDto;
import com.project.yeojeong.entity.Authority;
import com.project.yeojeong.entity.Member;
import com.project.yeojeong.exception.DuplicateMemberException;
import com.project.yeojeong.repository.MemberRepository;
import com.project.yeojeong.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Collections;
import java.util.UUID;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger LOGGER = LoggerFactory.getLogger(MemberService.class);

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 일반 회원가입
    @Transactional
    public MemberDto signup(MemberDto memberDto) {
        // 권한 정보를 만듦
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        // 권한정보도 넣어서 User 정보를 만듦
        Member member = Member.builder()
                .memberId(memberDto.getMemberId())
                .memberNickname(memberDto.getMemberNickname())
                .memberPw(passwordEncoder.encode(memberDto.getMemberPw()))
                .authorities(Collections.singleton(authority))
                .build();
        return MemberDto.from(memberRepository.save(member));
    }

    // 소셜 회원가입
    @Transactional
    public MemberDto signup(MemberDto memberDto, String id) {
        if (memberRepository.getByMemberId(memberDto.getMemberId()) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        // 권한 정보를 만듦
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        // 권한정보도 넣어서 User 정보를 만듦
        Member member = Member.builder()
                .memberId(memberDto.getMemberId())
                .memberNickname(memberDto.getMemberNickname())
                .memberPw(passwordEncoder.encode(UUID.randomUUID().toString()))
                .memberOauthKey(id)
                .authorities(Collections.singleton(authority))
                .build();
        return MemberDto.from(memberRepository.save(member));
    }


    // SecurityContext에 저장된 username의 정보만 가져옴옴
    @Transactional(readOnly = true)
    public MemberDto getMyUserWithAuthorities() {
        return MemberDto.from(SecurityUtil.getCurrentUserid().flatMap(memberRepository::findOneWithAuthoritiesByMemberId).orElse(null));
    }

    // id 중복체크
    @Transactional(readOnly = true)
    public Member memberIdDuplicateCheck(String memberId) {
        return memberRepository.getByMemberId(memberId);
    }

    // nick 중복체크
    @Transactional(readOnly = true)
    public Member memberNickNameDuplicateCheck(String memberNickname) {
        return memberRepository.getByMemberNickname(memberNickname);
    }

    // 회원 탈퇴
    @Transactional
    public Boolean memberWithdraw(Principal principal) {
        try {
            memberRepository.deleteById(memberRepository.getByMemberId(principal.getName()).getMemberNo());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 고유식별자 값으로 DB에 있는지 조회
    @Transactional(readOnly = true)
    public Member oAuthDuplicateCheck(String id) {
        return memberRepository.getByMemberOauthKey(id);
    }
}