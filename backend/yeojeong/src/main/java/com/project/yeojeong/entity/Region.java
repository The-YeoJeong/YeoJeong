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
    @Column(name = "region_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int region_no;

    private String region_name;
}
