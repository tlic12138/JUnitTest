package service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.xml.sax.InputSource;

import com.tlic.dao.IUserDao;
import com.tlic.dao.UserDao;
import com.tlic.model.User;
import com.tlic.util.DBUtil;

import static org.junit.Assert.*;

public class TestDBUnit {

	// 搭建DBUnit环境，并测试DatabaseOperation.CLEAN_INSERT.execute(con,ds);即先清空表再插入测试数据
	@Test
	public void testLoad() {
		try {
			// 首先备份数据库中内容
			testBackupTable();
			
			// 创建dbunit的Connection，需要传入一个数据库的connection作为参数
			IDatabaseConnection con = new DatabaseConnection(DBUtil.getConnection());
			
			/*
			 * FlatXmlDataSet用来获取基于属性存储的属性值(推荐，因为xml会简单些)
			 * XMLDataSet用来获取基于节点类型存储的属性值
			 */
			IDataSet ds = new FlatXmlDataSet(
					new FlatXmlProducer(
					new InputSource(
					TestDBUnit.class.getClassLoader().getResourceAsStream("t_user.xml"))));
			
			// 会将数据库中的数据清空，并且把测试数据插入
			// con：连接对象，ds：表操作对象
			DatabaseOperation.CLEAN_INSERT.execute(con,ds);
			
			// 从DAO中获取数据并且完成测试
			IUserDao ud = new UserDao();
			User tu = ud.load("admin");
			assertEquals(tu.getId(), 1);
			assertEquals(tu.getUsername(),"admin");
			assertEquals(tu.getPassword(),"123");
			assertEquals(tu.getNickname(),"超级管理员");
			
			
		} catch (DatabaseUnitException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 还原数据库
		testResume();
	}
	
	// 备份数据库所有表
	@Test
	public void testBackupAllTable() {
		try {
			// 创建dbunit的Connection，需要传入一个数据库的connection作为参数
			IDatabaseConnection con = new DatabaseConnection(DBUtil.getConnection());
			
			// 根据con创建相应的dataset，这个dataset包含了所有的表
			IDataSet ds = con.createDataSet();
			
			// 将ds中的数据通过FlatXmlDataSet的格式写到文件中(备份)
			FlatXmlDataSet.write(ds, new FileWriter("D:\\study\\EclipseWorkPlace\\JUnit02\\test.xml"));
		} catch (DataSetException e) {
			e.printStackTrace();
		} catch(DatabaseUnitException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 备份数据库某一张表
	@Test
	public void testBackupTable() {
		try {
			// 创建dbunit的Connection，需要传入一个数据库的connection作为参数
			IDatabaseConnection con = new DatabaseConnection(DBUtil.getConnection());
			
			// 通过QuertDataSet可以有效的选择要处理的表来作为数据集
			QueryDataSet backup = new QueryDataSet(con);
			
			// 添加t_user这张表作为备份表
			backup.addTable("t_user");
			FlatXmlDataSet.write(backup, new FileWriter("D:\\study\\EclipseWorkPlace\\JUnit02\\test.xml"));
		} catch (DatabaseUnitException | SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	// 还原数据库
	@Test
	public void testResume() {
		try {
			// 创建dbunit的Connection，需要传入一个数据库的connection作为参数
			IDatabaseConnection con = new DatabaseConnection(DBUtil.getConnection());
			
			// 根据备份文件创建dataset，读取到test.xml的内容
			IDataSet ds = new FlatXmlDataSet(
							new FlatXmlProducer(
							new InputSource(
							new FileInputStream("D:\\study\\EclipseWorkPlace\\JUnit02\\test.xml"))));
			
			// 清空数据库，把备份的文件内容(test.xml中的内容)写入数据库，实现备份恢复
			DatabaseOperation.CLEAN_INSERT.execute(con, ds);
		} catch (FileNotFoundException | DatabaseUnitException | SQLException e) {
			e.printStackTrace();
		}
	}
}
