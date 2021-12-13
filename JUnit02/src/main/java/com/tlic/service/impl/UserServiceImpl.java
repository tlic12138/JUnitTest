package com.tlic.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.tlic.dao.IUserDao;
import com.tlic.dao.UserDao;
import com.tlic.model.User;
import com.tlic.model.UserException;
import com.tlic.service.IUserService;

public class UserServiceImpl implements IUserService {
	
	// ע��IUserDao
	private IUserDao userDao;
	// ���췽��
	public UserServiceImpl(IUserDao userDao) {
		this.userDao = userDao;
	}
	public UserServiceImpl() {
		userDao = new UserDao();
	}

	@Override
	public void add(User user) {
		// ���username��Ϊnull��˵���û����Ѿ�����
		if(load(user.getUsername()) != null) {
			throw new UserException("�û����Ѿ�����");
		}
		userDao.add(user);
	}

	@Override
	public void delete(String username) {
		// ����û���Ϊ"admin"����ɾ��
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
		if(u == null) throw new UserException("�û���������");
		if(!u.getPassword().equals(u.getPassword())) throw new UserException("���벻��ȷ");
		return u;
	}

}
