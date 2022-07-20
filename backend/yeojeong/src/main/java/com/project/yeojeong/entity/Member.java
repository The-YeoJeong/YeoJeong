package com.project.yeojeong.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "member")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity{
    @Id
    @Column(name = "member_no", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberNo;

    private String memberId;

    private String memberPw;

    private String memberNickname;

    private String memberOauthKey;

    @ManyToMany // user와 authority 다대다 관계를 일대다, 다대일 관계의 조인테이블로 정의
    @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name = "member_no", referencedColumnName = "member_no")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;


}
