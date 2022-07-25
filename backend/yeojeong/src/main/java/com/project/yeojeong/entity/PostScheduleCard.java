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
public class PostScheduleCard extends BaseUpdateEntity{
    @Id
    @Column(name = "post_schedulecard_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int PostSchedulecardNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "post_datecard_no")
    private PostDateCard postDatecard;

    @Column(nullable = false, length = 30)
    private String postSchedulecardPlaceName;

    @Column(nullable = false, length = 100)
    private String postSchedulecardPlaceAddress;

    @Column(nullable = false, length = 200)
    private String postSchedulecardContent;

    public void updatePostDateCard(String postSchedulecardPlaceName,
                                   String postSchedulecardPlaceAddress,
                                   String postSchedulecardContent){
        this.postSchedulecardPlaceName = postSchedulecardPlaceName;
        this.postSchedulecardPlaceAddress = postSchedulecardPlaceAddress;
        this.postSchedulecardContent = postSchedulecardContent;
    }
}
