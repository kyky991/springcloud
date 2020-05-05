package com.zing.cloud.house.user.service;


import com.zing.cloud.house.user.model.User;

import java.util.List;

public interface IUserService {

    User getUserById(Long id);

    List<User> getUserByQuery(User user);

    boolean addAccount(User account, String enableUrl);

    boolean enable(String key);

    User auth(String username, String password);

    User getLoginUserByToken(String token);

    User getUserByEmail(String email);

    void invalidate(String token);

    User updateUser(User user);

    void resetNotify(String email, String url);

    String getResetKeyEmail(String key);

    User reset(String key, String password);
}
