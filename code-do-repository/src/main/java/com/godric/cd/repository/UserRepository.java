package com.godric.cd.repository;

import com.godric.cd.dao.UserDao;
import com.godric.cd.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private UserDao userDao;

    public int insert(UserPO user) {
        return userDao.insert(user);
    }

}
