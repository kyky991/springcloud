package com.zing.cloud.user.service;

import com.zing.cloud.user.dataobject.UserInfo;

public interface IUserService {

	/**
	 * 通过openid来查询用户信息
	 * @param openid
	 * @return
	 */
	UserInfo findByOpenid(String openid);
}
