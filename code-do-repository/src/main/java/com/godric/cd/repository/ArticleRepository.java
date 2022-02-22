package com.godric.cd.repository;

import com.godric.cd.dao.ArticleDao;
import com.godric.cd.po.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepository {

    @Autowired
    private ArticleDao articleDao;

    public int create(Article article) {
        return articleDao.insert(article);
    }

}
