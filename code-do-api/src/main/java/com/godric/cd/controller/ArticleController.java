package com.godric.cd.controller;

import com.godric.cd.request.ArticleCreateRequest;
import com.godric.cd.request.QueryArticleListRequest;
import com.godric.cd.result.BaseResult;
import com.godric.cd.result.DataResult;
import com.godric.cd.result.ListResult;
import com.godric.cd.result.PaginationResult;
import com.godric.cd.service.ArticleService;
import com.godric.cd.vo.ArticleDetailVO;
import com.godric.cd.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("create")
    public BaseResult create(@RequestBody @Validated ArticleCreateRequest request) {
        articleService.create(request);
        return BaseResult.success();
    }

    @PostMapping("queryList")
    public PaginationResult<ArticleVO> queryList(@RequestBody QueryArticleListRequest request) {
        return articleService.queryList(request);
    }

    @GetMapping("queryById")
    public DataResult<ArticleDetailVO> queryById(Long id) {
        return DataResult.success(null);
    }

}
