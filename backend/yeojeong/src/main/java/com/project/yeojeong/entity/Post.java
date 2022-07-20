package com.project.yeojeong.entity;

import com.project.yeojeong.dto.PostDateCardDto;
import com.project.yeojeong.dto.PostFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "travel_post")
@Getter
@Setter
@ToString
public class Post {
    @Id
    @Column(name = "post_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "member_no")
    private Member member;

    @Column(nullable = false, length = 40)
    private String postTitle;

    @Column(nullable = false)
    private Date postStartDate;

    @Column(nullable = false)
    private Date postEndDate;

    @Column(nullable = true)
    private String postContent;

    @Column(nullable = false)
    private int postHeartCnt;

    @Column(nullable = false)
    private boolean postOnlyme;

    public static Post createPost(PostFormDto postFormDto, Member member) {
        Post post = new Post();
        post.setMember(member);
        post.setPostStartDate(postFormDto.getPostStartDate());
        post.setPostEndDate(postFormDto.getPostEndDate());
        post.setPostContent(postFormDto.getPostContent());
        post.setPostOnlyme(postFormDto.getPostOnlyMe());
        return post;
    }
}
