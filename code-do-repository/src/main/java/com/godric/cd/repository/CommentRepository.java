package com.godric.cd.repository;

import com.godric.cd.dao.CommentDao;
import com.godric.cd.po.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

}
