package com.godric.cd.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.godric.cd.dao.CommentDao;
import com.godric.cd.po.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepository {

    @Autowired
    private CommentDao commentDao;

    public Comment queryById(Long id) {
        return commentDao.selectById(id);
    }

    public void addThumbNum(Comment comment) {
        comment.setThumbNum(comment.getThumbNum() + 1);
        this.update(comment);
    }

    public int create(Comment comment) {
        return commentDao.insert(comment);
    }

    public int update(Comment comment) {
        return commentDao.updateById(comment);
    }

    public List<Comment> queryByArticleId(Long articleId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId);
        wrapper.orderByDesc("id");

        return commentDao.selectList(wrapper);
    }

}
