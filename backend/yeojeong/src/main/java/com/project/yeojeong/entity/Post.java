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

    private String postTitle;

    private Date postStartDate;

    private Date postEndDate;

    private String postContent;

    private int postHeartCnt;

    private int postOnlyme;

    @OneToMany(mappedBy = "travel_post_datecard", cascade = CascadeType.ALL
            , orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PostDateCard> postDateCards = new ArrayList<>();

    public void addPostDateCard(PostDateCard postDateCard){
        postDateCards.add(postDateCard);
        //postDateCard.setPostNo(postNo);
    }

    public static Post createPost(PostFormDto postFormDto, Member member) {
        Post post = new Post();
        post.setMember(member);
        post.setPostStartDate(postFormDto.getPostStartDate());
        post.setPostEndDate(postFormDto.getPostEndDate());
        post.setPostContent(postFormDto.getPostContent());
        post.setPostOnlyme(postFormDto.getPostOnlyMe());

        //일자카드 리스트
        //List<PostDateCard> postDateCardList = postFormDto.getPostDateCard();
        //for(PostDateCard postDateCard : postDateCardDtoList) {
        //    postDateCard.setPostDatecardTitle(postFormDto.getPostDateCard().get().getPostDateCardTitle());
        //}

        return post;
    }
}
