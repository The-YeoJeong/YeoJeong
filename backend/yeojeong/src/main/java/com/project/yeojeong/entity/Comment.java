package com.project.yeojeong.entity;

import com.project.yeojeong.dto.CommentFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@Getter
@Setter
@ToString
public class Comment extends BaseEntity{
    @Id
    @Column(name = "comment_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "post_no")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "member_no")
    private Member member;

    @Column(nullable = false, length = 100)
    private String commentContent;

    public static Comment createComment(CommentFormDto commentFormDto, Member member, Post post) {
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setMember(member);
        comment.setCommentContent(commentFormDto.getCommentContent());
        return comment;
    }

    public void updateComment(CommentFormDto commentFormDto) {
        this.commentContent = commentFormDto.getCommentContent();
    }
}
