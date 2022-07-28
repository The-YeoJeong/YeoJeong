package com.project.yeojeong.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConditionDto {

    private String[] regionName;

    private Integer period;

    private boolean postContent;

    private boolean order;

    private String searchContent;
}
