package com.tlic.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.AmbiguousTableNameException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.xml.sax.InputSource;

public class AbstractDBUnitTestCase {

	public static IDatabaseConnection dbunitCon;
	
	private File tempFile;
	
	// 构造方法之前执行，只执行一次
	@BeforeClass
	public static void init() throws DatabaseUnitException, SQLException {
		dbunitCon = new DatabaseConnection(DBUtil.getConnection());
	}
	
	// 创建IDataSet
	protected IDataSet createDateSet(String tname) throws DataSetException {
		InputStream is = AbstractDBUnitTestCase
				.class
				.getClassLoader().getResourceAsStream("dbunit_xml"+tname+".xml");
		Assert.assertNotNull("dbunit的基本数据文件不存在",is);
		return new FlatXmlDataSet(new FlatXmlProducer(new InputSource(is)));
	}
	
	// 备份所有的表
	protected void backupAllTable() throws SQLException, IOException, DataSetException {
		IDataSet ds = dbunitCon.createDataSet();
		// 写备份文件
		writeBackupFile(ds);
	}
	
	// 写备份文件
	private void writeBackupFile(IDataSet ds) throws SQLException, IOException, DataSetException  {
		tempFile = File.createTempFile("back", ".xml");
		// 写入备份文件，前缀back后缀.xml
		FlatXmlDataSet.write(ds, new FileWriter(tempFile));
	}
	
	// 备份Custom表
	protected void backupCustomTable(String[] tname) throws DataSetException, SQLException, IOException {
		QueryDataSet ds = new QueryDataSet(dbunitCon);
		for(String str:tname) {
			ds.addTable(str);
		}
		// 写备份文件
		writeBackupFile(ds);
	}
	
	// 备份一张或几张表
	protected void backupOneTable(String tname) throws DataSetException, SQLException, IOException {
		// 备份一张或几张表
		backupCustomTable(new String[] {tname});
	}
	
	// 还原表
	protected void resumeTable() throws FileNotFoundException, DatabaseUnitException, SQLException {
		IDataSet ds = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(new FileInputStream(tempFile))));
		// 清空后存入xml中的数据
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
	}
	
	// 本类执行结束之前执行，只执行一次
	@AfterClass
	public static void destory() {
		if(dbunitCon != null)
			try {
				dbunitCon.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
