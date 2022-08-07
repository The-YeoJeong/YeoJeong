package com.project.yeojeong.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Table(name = "travel_post_region")
@Getter
@Setter
@ToString
public class PostRegion extends BaseUpdateEntity{
    @Id
    @Column(name = "post_region_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int post_region_no;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "post_no")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "region_no")
    private Region region;

}
