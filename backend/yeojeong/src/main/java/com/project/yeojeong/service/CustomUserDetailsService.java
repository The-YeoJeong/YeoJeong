package com.project.yeojeong.service;

import com.project.yeojeong.entity.Member;
import com.project.yeojeong.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String userid) {
        // 1. login 시에 db에서 유저정보와, 권한정보를 같이 가져옴
        return memberRepository.findOneWithAuthoritiesByMemberId(userid)
                .map(member -> createUser(userid, member))
                .orElseThrow(() -> new UsernameNotFoundException(userid + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String userid, Member member) {
        List<GrantedAuthority> grantedAuthorities = member.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());
        // 1의 정보를 기반으로 userdetails.User객체를 생성해서 return
        return new org.springframework.security.core.userdetails.User(member.getMemberId(),
                member.getMemberPw(),
                grantedAuthorities);
    }
}
