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
	
	// ���췽��֮ǰִ�У�ִֻ��һ��
	@BeforeClass
	public static void init() throws DatabaseUnitException, SQLException {
		dbunitCon = new DatabaseConnection(DBUtil.getConnection());
	}
	
	// ����IDataSet
	protected IDataSet createDateSet(String tname) throws DataSetException {
		InputStream is = AbstractDBUnitTestCase
				.class
				.getClassLoader().getResourceAsStream("dbunit_xml"+tname+".xml");
		Assert.assertNotNull("dbunit�Ļ��������ļ�������",is);
		return new FlatXmlDataSet(new FlatXmlProducer(new InputSource(is)));
	}
	
	// �������еı�
	protected void backupAllTable() throws SQLException, IOException, DataSetException {
		IDataSet ds = dbunitCon.createDataSet();
		// д�����ļ�
		writeBackupFile(ds);
	}
	
	// д�����ļ�
	private void writeBackupFile(IDataSet ds) throws SQLException, IOException, DataSetException  {
		tempFile = File.createTempFile("back", ".xml");
		// д�뱸���ļ���ǰ׺back��׺.xml
		FlatXmlDataSet.write(ds, new FileWriter(tempFile));
	}
	
	// ����Custom��
	protected void backupCustomTable(String[] tname) throws DataSetException, SQLException, IOException {
		QueryDataSet ds = new QueryDataSet(dbunitCon);
		for(String str:tname) {
			ds.addTable(str);
		}
		// д�����ļ�
		writeBackupFile(ds);
	}
	
	// ����һ�Ż��ű�
	protected void backupOneTable(String tname) throws DataSetException, SQLException, IOException {
		// ����һ�Ż��ű�
		backupCustomTable(new String[] {tname});
	}
	
	// ��ԭ��
	protected void resumeTable() throws FileNotFoundException, DatabaseUnitException, SQLException {
		IDataSet ds = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(new FileInputStream(tempFile))));
		// ��պ����xml�е�����
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
	}
	
	// ����ִ�н���֮ǰִ�У�ִֻ��һ��
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
