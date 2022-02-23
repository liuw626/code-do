package com.godric.cd.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.godric.cd.dao.ArticleDao;
import com.godric.cd.po.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleRepository {

    @Autowired
    private ArticleDao articleDao;

    public int create(Article article) {
        return articleDao.insert(article);
    }

    public Article queryById(Long id) {
        return articleDao.selectById(id);
    }

    public void addThumbNum(Article article) {
        article.setThumbNum(article.getThumbNum() + 1);
        this.update(article);
    }

    public void addCollectNum(Article article) {
        article.setCollectNum(article.getCollectNum() + 1);
        this.update(article);
    }

    public void addViewNum(Article article) {
        article.setViewNum(article.getViewNum() + 1);
        this.update(article);
    }

    public IPage<Article> queryPage(String keyword, Integer curPage, Integer pageSize) {
        IPage<Article> page = new Page<>(curPage, pageSize);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.like("title", keyword);
        return articleDao.selectPage(page, wrapper);
    }

    public int update(Article article) {
        return articleDao.updateById(article);
    }

}
