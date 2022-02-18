package com.godric.cd.service;

import com.godric.cd.po.UserPO;
import com.godric.cd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public int insert(String username, String avatar, String openId, String unionId) {
        UserPO user = UserPO.builder().username(username).avatar(avatar).openId(openId).unionId(unionId).build();
        return userRepository.insert(user);
    }

}
