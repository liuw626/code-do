package com.godric.cd.repository;

import com.godric.cd.dao.CategoryDao;
import com.godric.cd.po.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository {

    @Autowired
    private CategoryDao categoryDao;

    public Category queryById(Long id) {
        return categoryDao.selectById(id);
    }

}
