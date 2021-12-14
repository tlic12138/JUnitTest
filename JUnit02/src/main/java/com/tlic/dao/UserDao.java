package com.tlic.dao;

import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tlic.model.User;
import com.tlic.util.DBUtil;

public class UserDao implements IUserDao {

	@Override
	public void add(User user) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			// 获取连接
			con = DBUtil.getConnection();
			// 写sql
			String sql = "insert into t_user(username,password,nickname) value(?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getNickname());
			// 执行update
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(ps);
			DBUtil.close(con);
		}
	}

	@Override
	public void delete(String username) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			// 获取连接
			con = DBUtil.getConnection();
			// 写sql
			String sql = "delete from t_user where username=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, username);
			// 执行update
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(ps);
			DBUtil.close(con);
		}
	}
	
	@Override
	public User load(String username) {
		User u = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// 获取连接
			con = DBUtil.getConnection();
			// 写sql
			String sql = "select * from t_user where username=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, username);
			// 执行select，获取结果集
			rs = ps.executeQuery();
			if (rs.next()) { 
				if(u == null) {
					u = new User();
				}
				// 给User对象赋值
				u.setId(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("password"));
				u.setNickname(rs.getString("nickname"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(con);
		}
		return u;
	}

}
