package com.project.yeojeong.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Table(name = "heart")
@Getter
@Setter
@ToString
public class Heart extends BaseCreateEntity{
    @Id
    @Column(name = "heart_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int heartNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "post_no")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "member_no")
    private Member member;

    public static Heart createHeart(Post post, Member member) {
        Heart heart = new Heart();
        heart.setPost(post);
        heart.setMember(member);
        return heart;
    }
}
