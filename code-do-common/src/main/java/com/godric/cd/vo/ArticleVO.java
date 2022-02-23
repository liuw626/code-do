package com.godric.cd.vo;

import lombok.Data;

@Data
public class ArticleVO {

    private Long id;

    private String title;

    private String authorName;

    private String authorAvatar;

    private Long thumbNum;

    private Long viewNum;

    private Long collectNum;

}
