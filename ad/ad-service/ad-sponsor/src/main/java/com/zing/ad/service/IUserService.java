package com.zing.ad.service;

import com.zing.ad.exception.AdException;
import com.zing.ad.vo.CreateUserRequest;
import com.zing.ad.vo.CreateUserResponse;

/**
 * @author Zing
 * @date 2019-11-22
 */
public interface IUserService {

    CreateUserResponse createUser(CreateUserRequest request) throws AdException;

}
