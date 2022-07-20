package com.project.yeojeong.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Table(name = "travel_post_schedulecard")
@Getter
@Setter
@ToString
public class PostScheduleCard {
    @Id
    @Column(name = "post_schedulecard_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int PostSchedulecardNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "post_datecard_no")
    private PostDateCard postDatecard;

    private String postSchedulecardPlaceName;

    private String getPostSchedulecardPlaceAddress;

    private String getPostSchedulecardPlaceContent;
}
