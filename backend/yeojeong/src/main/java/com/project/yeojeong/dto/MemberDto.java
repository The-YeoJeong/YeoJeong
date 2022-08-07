package com.project.yeojeong.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.yeojeong.entity.Member;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    @NotNull
    private String memberId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String memberPw;

    private String memberNickname;

    private String memberOauthKey;

    private Set<AuthorityDto> authorityDtoSet;

    // User Entity에서 UserDto
    public static MemberDto from(Member member) {
        if(member == null) return null;
        return MemberDto.builder()
                .memberId(member.getMemberId())
                .memberNickname(member.getMemberNickname())
                .authorityDtoSet(member.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}