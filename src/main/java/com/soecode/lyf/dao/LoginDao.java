package com.soecode.lyf.dao;

import java.util.List;
import java.util.Map;

import com.soecode.lyf.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author rcp
 */
@Repository
public interface LoginDao {

	/**
	 * 用户登录查询
	 * @param map
	 * @return
	 */
	User doLogin(Map<String, String> map);

	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	int register(User user);

	/**
	 * 获取所有用户
	 * @return
	 */
	List<User> getAllUser();
}
