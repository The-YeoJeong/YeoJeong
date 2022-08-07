package com.project.yeojeong.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "region")
@Getter
@Setter
@ToString
public class Region {
    @Id
    @Column(name = "region_no", length = 2)
    private String regionNo;
    @Column(nullable = false, length = 4)
    private String regionName;
}
