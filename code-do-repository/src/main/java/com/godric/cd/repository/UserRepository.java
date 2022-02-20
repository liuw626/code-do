package com.godric.cd.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.godric.cd.dao.UserDao;
import com.godric.cd.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private UserDao userDao;

    public UserPO insert(String username, String avatar, String openId) {
        UserPO user = UserPO.builder().username(username).avatar(avatar).openId(openId).build();
        userDao.insert(user);
        return user;
    }

    public UserPO queryByOpenId(String openId) {
        QueryWrapper<UserPO> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id", openId);

        List<UserPO> users = userDao.selectList(wrapper);
        return CollectionUtils.firstElement(users);
    }

}
