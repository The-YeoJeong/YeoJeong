package com.project.yeojeong.config;

import com.project.yeojeong.repository.MemberRepository;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    private MemberRepository memberRepository;
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String memberId = "";
        if(authentication != null){
            if (authentication.getName().equals("anonymousUser")) {
                memberId = "anonymous";
            } else {
                memberId = authentication.getName() ;
            }

        }
        return Optional.of(memberId);
    }
}
