package com.godric.cd.service;

import com.godric.cd.po.Article;
import com.godric.cd.repository.ArticleRepository;
import com.godric.cd.request.ArticleCreateRequest;
import com.godric.cd.utils.SessionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public void create(ArticleCreateRequest request) {
        Long uid = SessionUtil.getUid();
        // todo 校验labels & category
        Article article = Article.builder()
                                 .title(request.getTitle())
                                 .content(request.getContent())
                                 .categoryId(request.getCategoryId())
                                 .labelIds(StringUtils.join(request.getLabelIds(), ","))
                                 .uid(uid).build();

        articleRepository.create(article);
    }


}
