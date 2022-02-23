package com.godric.cd.repository;

import com.godric.cd.dao.LabelDao;
import com.godric.cd.po.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LabelRepository {

    @Autowired
    private LabelDao labelDao;

    public List<Label> queryByIds(List<Long> id) {
        return labelDao.selectBatchIds(id);
    }

}
