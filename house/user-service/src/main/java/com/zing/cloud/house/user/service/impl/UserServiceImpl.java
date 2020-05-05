package com.zing.cloud.house.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.zing.cloud.house.user.common.UserException;
import com.zing.cloud.house.user.mapper.UserMapper;
import com.zing.cloud.house.user.model.User;
import com.zing.cloud.house.user.service.IMailService;
import com.zing.cloud.house.user.service.IUserService;
import com.zing.cloud.house.user.utils.BeanHelper;
import com.zing.cloud.house.user.utils.HashUtils;
import com.zing.cloud.house.user.utils.JwtHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IMailService mailService;

    @Value("${file.path:}")
    private String FILE_PATH;

    @Value("${file.prefix}")
    private String IMG_PREFIX;

    /**
     * 1.首先通过缓存获取
     * 2.不存在将从通过数据库获取用户对象
     * 3.将用户对象写入缓存，设置缓存时间5分钟
     * 4.返回对象
     *
     * @param id
     * @return
     */
    @Override
    public User getUserById(Long id) {
        String key = "user:" + id;
        String json = redisTemplate.opsForValue().get(key);
        User user = null;
        if (Strings.isNullOrEmpty(json)) {
            user = userMapper.selectById(id);
            user.setAvatar(IMG_PREFIX + user.getAvatar());
            String string = JSON.toJSONString(user);
            redisTemplate.opsForValue().set(key, string);
            redisTemplate.expire(key, 5, TimeUnit.MINUTES);
        } else {
            user = JSON.parseObject(json, User.class);
        }
        return user;
    }

    @Override
    public List<User> getUserByQuery(User user) {
        List<User> list = userMapper.select(user);
        list.forEach(u -> u.setAvatar(IMG_PREFIX + u.getAvatar()));
        return list;
    }

    /**
     * 注册
     *
     * @param account
     * @return
     */
    @Override
    public boolean addAccount(User account, String enableUrl) {
        account.setPasswd(HashUtils.encryptPassword(account.getPasswd()));
        BeanHelper.onInsert(account);
        userMapper.insert(account);
        registerNotify(account.getEmail(), enableUrl);
        return true;
    }

    /**
     * 发送注册激活邮件
     *
     * @param email
     * @param enableUrl
     */
    private void registerNotify(String email, String enableUrl) {
        String randomKey = HashUtils.hashString(email) + RandomStringUtils.randomAlphabetic(10);
        redisTemplate.opsForValue().set(randomKey, email);
        redisTemplate.expire(randomKey, 1, TimeUnit.HOURS);
        String content = enableUrl + "?key=" + randomKey;
        mailService.sendSimpleMail("房产平台激活邮件", content, email);
    }

    @Override
    public boolean enable(String key) {
        String email = redisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(email)) {
            throw new UserException(UserException.Type.USER_NOT_FOUND, "无效的key");
        }
        User user = new User();
        user.setEmail(email);
        user.setEnable(1);
        userMapper.update(user);
        return true;
    }

    /**
     * 校验用户名密码、生成token并返回用户对象
     *
     * @param email
     * @param passwd
     * @return
     */
    @Override
    public User auth(String email, String passwd) {
        if (StringUtils.isBlank(email) || StringUtils.isBlank(passwd)) {
            throw new UserException(UserException.Type.USER_AUTH_FAIL, "User Auth Fail");
        }

        User user = new User();
        user.setEmail(email);
        user.setPasswd(HashUtils.encryptPassword(passwd));
        user.setEnable(1);
        List<User> list = getUserByQuery(user);
        if (!list.isEmpty()) {
            User retUser = list.get(0);
            onLogin(retUser);
            return retUser;
        }
        throw new UserException(UserException.Type.USER_AUTH_FAIL, "User Auth Fail");
    }

    private void onLogin(User user) {
        String token = JwtHelper.genToken(ImmutableMap.of("email", user.getEmail(), "name", user.getName(), "ts", Instant.now().getEpochSecond() + ""));
        renewToken(token, user.getEmail());
        user.setToken(token);
    }

    private String renewToken(String token, String email) {
        redisTemplate.opsForValue().set(email, token);
        redisTemplate.expire(email, 30, TimeUnit.MINUTES);
        return token;
    }

    @Override
    public User getLoginUserByToken(String token) {
        Map<String, String> map = null;
        try {
            map = JwtHelper.verifyToken(token);
        } catch (Exception e) {
            throw new UserException(UserException.Type.USER_NOT_LOGIN, "User not login");
        }
        String email = map.get("email");
        Long expired = redisTemplate.getExpire(email);
        if (expired > 0L) {
            renewToken(token, email);
            User user = getUserByEmail(email);
            user.setToken(token);
            return user;
        }
        throw new UserException(UserException.Type.USER_NOT_LOGIN, "user not login");
    }

    @Override
    public User getUserByEmail(String email) {
        User queryUser = new User();
        queryUser.setEmail(email);
        List<User> users = getUserByQuery(queryUser);
        if (!users.isEmpty()) {
            return users.get(0);
        }
        throw new UserException(UserException.Type.USER_NOT_FOUND, "User not found for " + email);
    }

    @Override
    public void invalidate(String token) {
        Map<String, String> map = JwtHelper.verifyToken(token);
        redisTemplate.delete(map.get("email"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User updateUser(User user) {
        if (user.getEmail() == null) {
            return null;
        }
        if (!Strings.isNullOrEmpty(user.getPasswd())) {
            user.setPasswd(HashUtils.encryptPassword(user.getPasswd()));
        }
        userMapper.update(user);
        return userMapper.selectByEmail(user.getEmail());
    }

    @Override
    public void resetNotify(String email, String url) {
        String randomKey = "reset_" + RandomStringUtils.randomAlphabetic(10);
        redisTemplate.opsForValue().set(randomKey, email);
        redisTemplate.expire(randomKey, 1, TimeUnit.HOURS);
        String content = url + "?key=" + randomKey;
        mailService.sendSimpleMail("房产平台重置密码邮件", content, email);
    }

    @Override
    public String getResetKeyEmail(String key) {
        return redisTemplate.opsForValue().get(key);
    }


    /**
     * 重置密码操作
     *
     * @param key
     * @param password
     * @return
     */
    @Override
    public User reset(String key, String password) {
        String email = getResetKeyEmail(key);
        User updateUser = new User();
        updateUser.setEmail(email);
        updateUser.setPasswd(HashUtils.encryptPassword(password));
        userMapper.update(updateUser);
        return getUserByEmail(email);
    }

}
