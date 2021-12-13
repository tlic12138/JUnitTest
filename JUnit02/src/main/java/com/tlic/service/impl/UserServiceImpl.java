package com.tlic.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.tlic.dao.IUserDao;
import com.tlic.dao.UserDao;
import com.tlic.model.User;
import com.tlic.model.UserException;
import com.tlic.service.IUserService;

public class UserServiceImpl implements IUserService {
	
	// 注入IUserDao
	private IUserDao userDao;
	// 构造方法
	public UserServiceImpl(IUserDao userDao) {
		this.userDao = userDao;
	}
	public UserServiceImpl() {
		userDao = new UserDao();
	}

	@Override
	public void add(User user) {
		// 如果username不为null，说明用户名已经存在
		if(load(user.getUsername()) != null) {
			throw new UserException("用户名已经存在");
		}
		userDao.add(user);
	}

	@Override
	public void delete(String username) {
		// 如果用户名为"admin"则不能删除
		// if(username.equals("admin")) return;
		userDao.delete(username);
	}

	@Override
	public User load(String username) {
		return userDao.load(username);
	}

	@Override
	public User login(String username, String password) {
		User u = load(username);
		if(u == null) throw new UserException("用户名不存在");
		if(!u.getPassword().equals(u.getPassword())) throw new UserException("密码不正确");
		return u;
	}

}
