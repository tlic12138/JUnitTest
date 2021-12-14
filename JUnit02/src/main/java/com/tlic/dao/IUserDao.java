package com.tlic.dao;

import com.tlic.model.User;

public interface IUserDao {

	public void add(User user);
	public void delete(String username);
	public User load(String username);
}
