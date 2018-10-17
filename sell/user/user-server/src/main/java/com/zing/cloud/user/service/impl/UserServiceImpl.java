package com.zing.cloud.user.service.impl;

import com.zing.cloud.user.dataobject.UserInfo;
import com.zing.cloud.user.repository.UserInfoRepostory;
import com.zing.cloud.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserInfoRepostory repostory;

    /**
     * 通过openid来查询用户信息
     *
     * @param openid
     * @return
     */
    @Override
    public UserInfo findByOpenid(String openid) {
        return repostory.findByOpenid(openid);
    }
}
