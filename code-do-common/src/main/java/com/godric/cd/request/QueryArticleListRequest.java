package com.godric.cd.request;

import lombok.Data;

@Data
public class QueryArticleListRequest {

    private String keyword;

    private Integer curPage;

    private Integer pageSize;

}
