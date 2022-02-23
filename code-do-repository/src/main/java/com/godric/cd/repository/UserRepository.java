package com.godric.cd.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.godric.cd.dao.UserDao;
import com.godric.cd.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@Repository
public class UserRepository {

    @Autowired
    private UserDao userDao;

    public User insert(String username, String avatar, String openId) {
        User user = User.builder().username(username).avatar(avatar).openId(openId).build();
        userDao.insert(user);
        return user;
    }

    public int update(User user) {
        return userDao.updateById(user);
    }

    public User queryByOpenId(String openId) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id", openId);

        List<User> users = userDao.selectList(wrapper);
        return CollectionUtils.firstElement(users);
    }

    public User queryById(Long id) {
        return userDao.selectById(id);
    }

    public List<User> queryList(Set<Long> uids) {
        return userDao.selectBatchIds(uids);
    }
}
