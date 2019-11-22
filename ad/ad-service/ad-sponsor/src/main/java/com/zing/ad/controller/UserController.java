package com.zing.ad.controller;

import com.alibaba.fastjson.JSON;
import com.zing.ad.exception.AdException;
import com.zing.ad.service.IUserService;
import com.zing.ad.vo.CreateUserRequest;
import com.zing.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zing
 * @date 2019-11-22
 */
@Slf4j
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/user/create")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) throws AdException {
        log.info("ad-sponsor: createUser -> {}", JSON.toJSONString(request));
        return userService.createUser(request);
    }

}
