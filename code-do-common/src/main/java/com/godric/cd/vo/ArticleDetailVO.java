package com.godric.cd.vo;

import lombok.Data;

import java.util.List;

@Data
public class ArticleDetailVO {

    private Long id;

    private String title;

    private String content;

    private String authorName;

    private String authorAvatar;

    private List<String> comments;

}
