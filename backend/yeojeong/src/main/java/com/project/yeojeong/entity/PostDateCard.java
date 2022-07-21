package com.project.yeojeong.entity;

import com.project.yeojeong.dto.PostDateCardDto;
import com.project.yeojeong.dto.PostFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "travel_post_datecard")
@Getter
@Setter
@ToString
public class PostDateCard extends BaseEntity{
    @Id
    @Column(name = "post_datecard_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postDatecardNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "post_no")
    private Post post;

    @Column(nullable = false, length = 40)
    private String postDatecardTitle;

    public void updatePostDateCard(String postDatecardTitle){
        this.postDatecardTitle = postDatecardTitle;
    }

//    public static PostDateCard createPostDateCard(PostFormDto postFormDto, Post post) {
//        PostDateCard postDateCard = new PostDateCard();
//        postDateCard.setPostNo(post);
//        postDateCard.setPostDatecardTitle(postFormDto.getPostDateCard().get(0).getPostDateCardTitle());
//    }
}
