package com.tlic.junit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;

import com.tlic.model.User;
import com.tlic.util.DBUtil;

public class TestA {

	@Test
	public void add() {
		User user = new User("admin", "3425", "tlic");
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
		} finally {
			DBUtil.close(ps);
			DBUtil.close(con);
		}
	}
}
