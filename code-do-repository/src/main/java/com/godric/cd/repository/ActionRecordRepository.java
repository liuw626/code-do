package com.godric.cd.repository;

import com.godric.cd.dao.ActionRecordDao;
import com.godric.cd.po.ActionRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ActionRecordRepository {

    @Autowired
    private ActionRecordDao actionRecordDao;

    public int create(ActionRecord actionRecord) {
        return actionRecordDao.insert(actionRecord);
    }

}
