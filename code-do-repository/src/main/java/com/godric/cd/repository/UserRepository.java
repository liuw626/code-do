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

    public int insert(UserPO user) {
        return userDao.insert(user);
    }

    public UserPO queryByOpenIdAndUnionId(String openId, String unionId) {
        QueryWrapper<UserPO> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id", openId)
               .eq("union_id", unionId);

        List<UserPO> users = userDao.selectList(wrapper);
        return CollectionUtils.firstElement(users);
    }

}
