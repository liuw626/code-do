package com.godric.cd.controller;

import com.godric.cd.request.ArticleCreateRequest;
import com.godric.cd.result.BaseResult;
import com.godric.cd.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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

}
