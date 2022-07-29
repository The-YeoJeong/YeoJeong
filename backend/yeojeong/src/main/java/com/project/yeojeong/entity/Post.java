package com.project.yeojeong.entity;

import com.project.yeojeong.dto.PostFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "travel_post")
@Getter
@Setter
@ToString
public class Post extends BaseUpdateEntity{
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
    private Date postStartdate;

    @Column(nullable = false)
    private Date postEnddate;

    @Column(nullable = true)
    private String postContent;

    @Column(nullable = false)
    private int postHeartCnt;

    @Column(nullable = false)
    private boolean postOnlyme;

    @Column(name = "date_diff")
    private Integer dateDiff;

    public static Post createPost(PostFormDto postFormDto, Member member) {
        Post post = new Post();
        post.setMember(member);
        post.setPostTitle(postFormDto.getPostTitle());
        post.setPostStartdate(postFormDto.getPostStartDate());
        post.setPostEnddate(postFormDto.getPostEndDate());
        post.setPostContent(postFormDto.getPostContent());
        post.setPostOnlyme(postFormDto.isPostOnlyMe());
        post.setDateDiff((int)(postFormDto.getPostEndDate().getTime() - postFormDto.getPostStartDate().getTime()) / 1000 / (24*60*60));
        return post;
    }

    public void updatePost(PostFormDto postDto) {
        this.postTitle = postDto.getPostTitle();
        this.postStartdate = postDto.getPostStartDate();
        this.postEnddate = postDto.getPostEndDate();
        this.postContent = postDto.getPostContent();
        this.postOnlyme = postDto.isPostOnlyMe();
    }
}
