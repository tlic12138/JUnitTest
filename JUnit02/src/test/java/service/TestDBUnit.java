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

	// �DBUnit������������DatabaseOperation.CLEAN_INSERT.execute(con,ds);������ձ��ٲ����������
	@Test
	public void testLoad() {
		try {
			// ���ȱ������ݿ�������
			testBackupTable();
			
			// ����dbunit��Connection����Ҫ����һ�����ݿ��connection��Ϊ����
			IDatabaseConnection con = new DatabaseConnection(DBUtil.getConnection());
			
			/*
			 * FlatXmlDataSet������ȡ�������Դ洢������ֵ(�Ƽ�����Ϊxml���Щ)
			 * XMLDataSet������ȡ���ڽڵ����ʹ洢������ֵ
			 */
			IDataSet ds = new FlatXmlDataSet(
					new FlatXmlProducer(
					new InputSource(
					TestDBUnit.class.getClassLoader().getResourceAsStream("t_user.xml"))));
			
			// �Ὣ���ݿ��е�������գ����ҰѲ������ݲ���
			// con�����Ӷ���ds�����������
			DatabaseOperation.CLEAN_INSERT.execute(con,ds);
			
			// ��DAO�л�ȡ���ݲ�����ɲ���
			IUserDao ud = new UserDao();
			User tu = ud.load("admin");
			assertEquals(tu.getId(), 1);
			assertEquals(tu.getUsername(),"admin");
			assertEquals(tu.getPassword(),"123");
			assertEquals(tu.getNickname(),"��������Ա");
			
			
		} catch (DatabaseUnitException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// ��ԭ���ݿ�
		testResume();
	}
	
	// �������ݿ����б�
	@Test
	public void testBackupAllTable() {
		try {
			// ����dbunit��Connection����Ҫ����һ�����ݿ��connection��Ϊ����
			IDatabaseConnection con = new DatabaseConnection(DBUtil.getConnection());
			
			// ����con������Ӧ��dataset�����dataset���������еı�
			IDataSet ds = con.createDataSet();
			
			// ��ds�е�����ͨ��FlatXmlDataSet�ĸ�ʽд���ļ���(����)
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
	
	// �������ݿ�ĳһ�ű�
	@Test
	public void testBackupTable() {
		try {
			// ����dbunit��Connection����Ҫ����һ�����ݿ��connection��Ϊ����
			IDatabaseConnection con = new DatabaseConnection(DBUtil.getConnection());
			
			// ͨ��QuertDataSet������Ч��ѡ��Ҫ����ı�����Ϊ���ݼ�
			QueryDataSet backup = new QueryDataSet(con);
			
			// ���t_user���ű���Ϊ���ݱ�
			backup.addTable("t_user");
			FlatXmlDataSet.write(backup, new FileWriter("D:\\study\\EclipseWorkPlace\\JUnit02\\test.xml"));
		} catch (DatabaseUnitException | SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	// ��ԭ���ݿ�
	@Test
	public void testResume() {
		try {
			// ����dbunit��Connection����Ҫ����һ�����ݿ��connection��Ϊ����
			IDatabaseConnection con = new DatabaseConnection(DBUtil.getConnection());
			
			// ���ݱ����ļ�����dataset����ȡ��test.xml������
			IDataSet ds = new FlatXmlDataSet(
							new FlatXmlProducer(
							new InputSource(
							new FileInputStream("D:\\study\\EclipseWorkPlace\\JUnit02\\test.xml"))));
			
			// ������ݿ⣬�ѱ��ݵ��ļ�����(test.xml�е�����)д�����ݿ⣬ʵ�ֱ��ݻָ�
			DatabaseOperation.CLEAN_INSERT.execute(con, ds);
		} catch (FileNotFoundException | DatabaseUnitException | SQLException e) {
			e.printStackTrace();
		}
	}
}
