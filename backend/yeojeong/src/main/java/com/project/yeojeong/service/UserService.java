package com.project.yeojeong.service;

import com.project.yeojeong.dto.UserDto;
import com.project.yeojeong.entity.Authority;
import com.project.yeojeong.entity.User;
import com.project.yeojeong.exception.DuplicateMemberException;
import com.project.yeojeong.repository.UserRepository;
import com.project.yeojeong.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository , PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입
    @Transactional
    public UserDto signup(UserDto userDto) {
        if (userRepository.findById(userDto.getUserid()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        // 권한 정보를 만듦
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        // 권한정보도 넣어서 User 정보를 만듦
        User user = User.builder()
                .userid(userDto.getUserid())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .authorities(Collections.singleton(authority))
                .build();
        return UserDto.from(userRepository.save(user));
    }


    // SecurityContext에 저장된 username의 정보만 가져옴옴
   @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
       return UserDto.from(SecurityUtil.getCurrentUserid().flatMap(userRepository::findOneWithAuthoritiesByUserid).orElse(null));
    }

}