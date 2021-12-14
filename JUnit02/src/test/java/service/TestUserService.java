package service;

import static org.junit.Assert.fail;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tlic.dao.UserStubDao;
import com.tlic.model.User;
import com.tlic.model.UserException;
import com.tlic.service.IUserService;
import com.tlic.service.impl.UserServiceImpl;

/*
 * ��Ϊд������ʱ�������п����Ȱ�����Ҫ���Եķ���д���������ݺ�ӣ�
 * �п��ܳ���ûд���ݵĲ��Է����������Ĳ��Է���ͨ���˲���ȴû���κ����壬
 * �����ڴ���ӵ�ʱ�����ڲ��Է����м���fail("�������ӵĲ��Դ���")��
 * ��ֹ�ղ��Է���ͨ������
 */
public class TestUserService {
	
	private IUserService us;
	
	private User baseUser;
	
	@BeforeClass // ��ִ�й��캯��֮ǰִ�������������ִ��һ��
	public static void init() {
		System.out.println("before class");
	}
	
	// ��ʼ��
	@Before
	public void setUp() {
		us = new UserServiceImpl(new UserStubDao());
		baseUser = new User("admin","123","����Ա");
	}
	
	// ���������������Ƚ����������Ƿ���ͬ
	private void assertUserEquals(User u,User tu) {
		// �жϴ�����User����ʹӱ����ó���User�����Ƿ���ͬ
		assertEquals(tu.getUsername(), u.getUsername());
		assertEquals(tu.getPassword(), u.getPassword());
		assertEquals(tu.getNickname(), u.getNickname());
	}
	
	// �����û�����
	@Test
	public void testAdd() {
		// �ó�ʼ����User�����u��ֵ
		User u = baseUser;
		// ��User����u�������ݿ����
		us.add(u);
		// �����ݿ������ȡusernameΪ"admin"��User����
		User tu = us.load("admin");
		// �ж��û��Ƿ����
		assertNotNull("�û�������",tu);
		// �жϴ�����User����ʹӱ����ó���User�����Ƿ���ͬ
		assertUserEquals(u, tu);
	}
	
	// �û��Ѵ��ڲ���
	@Test(expected = UserException.class)
	public void testAddExistUsername() {
		us.add(baseUser);
		User tu = new User(1,"admin","123","123");
		us.add(tu);
	}
	
	// ɾ���û�����
	@Test
	public void testDelete() {
		// �Ƚ�User������ӽ���
		us.add(baseUser);
		// �ӱ���ȡ��User����
		User tu = us.load(baseUser.getUsername());
		// �ж�tu�Ƿ�Ϊ�գ���Ϊ������ȷ
		assertNotNull(tu);
		// ͨ��usernameɾ��User����
		us.delete(baseUser.getUsername());
		// �ٴδӱ���ȡ��User����
		tu = us.load(baseUser.getUsername());
		// ɾ����Ϊ�ղ���ȷ
		//��service�м����û���Ϊ"admin"ʱ��ɾ�����߼������Իᱨ�������������޸Ĵ������ٵ��ҳ��´������������
		assertNull("ɾ�����û�������",tu);
	}
	
	// ��¼����
	@Test
	public void testLogin() {
		// ����User
		us.add(baseUser);
		int id = 1;
		String username = "admin";
		String password = "123";
		User tu = new User(id,username,password,"123");
		// �жϴ�����User����ʹӱ����ó���User�����Ƿ���ͬ
		assertEquals(tu.getUsername(), baseUser.getUsername());
		assertEquals(tu.getPassword(), baseUser.getPassword());
	}
	
	// �û��������ڲ���
	@Test(expected = UserException.class)
	public void testNotExistsUserLogin() {
		// ����м���User
		us.add(baseUser);
		// ��һ�������ڵ��û���
		String username = "admin1";
		String password = "123";
		// ����login������Ӧ�ñ�UserException�쳣
		us.login(username, password);
	}
	
	// ����������
	@Test(expected = UserException.class)
	public void testPasswordErrorUserLogin() {
		// ����м���User
		us.add(baseUser);
		// ��һ�������ڵ��û���
		String username = "admin";
		String password = "1234";
		// ����login������Ӧ�ñ�UserException�쳣
		us.login(username, password);
	}
}
