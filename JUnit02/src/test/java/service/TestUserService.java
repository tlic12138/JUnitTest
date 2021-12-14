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
 * 因为写测试类时，我们有可能先把所有要测试的方案写出来，内容后加，
 * 有可能出现没写内容的测试方法，这样的测试方法通过了测试却没有任何意义，
 * 所以在搭架子的时候，先在测试方法中加上fail("请加入添加的测试代码")，
 * 防止空测试方法通过测试
 */
public class TestUserService {
	
	private IUserService us;
	
	private User baseUser;
	
	@BeforeClass // 在执行构造函数之前执行这个方法，仅执行一次
	public static void init() {
		System.out.println("before class");
	}
	
	// 初始化
	@Before
	public void setUp() {
		us = new UserServiceImpl(new UserStubDao());
		baseUser = new User("admin","123","管理员");
	}
	
	// 公共方法，用来比较两个对象是否相同
	private void assertUserEquals(User u,User tu) {
		// 判断存进表的User对象和从表中拿出的User对象是否相同
		assertEquals(tu.getUsername(), u.getUsername());
		assertEquals(tu.getPassword(), u.getPassword());
		assertEquals(tu.getNickname(), u.getNickname());
	}
	
	// 新增用户测试
	@Test
	public void testAdd() {
		// 用初始化的User对象给u赋值
		User u = baseUser;
		// 把User对象u存入数据库表中
		us.add(u);
		// 从数据库表中提取username为"admin"的User对象
		User tu = us.load("admin");
		// 判断用户是否存在
		assertNotNull("用户不存在",tu);
		// 判断存进表的User对象和从表中拿出的User对象是否相同
		assertUserEquals(u, tu);
	}
	
	// 用户已存在测试
	@Test(expected = UserException.class)
	public void testAddExistUsername() {
		us.add(baseUser);
		User tu = new User(1,"admin","123","123");
		us.add(tu);
	}
	
	// 删除用户测试
	@Test
	public void testDelete() {
		// 先将User对象添加进表
		us.add(baseUser);
		// 从表中取出User对象
		User tu = us.load(baseUser.getUsername());
		// 判断tu是否为空，不为空则正确
		assertNotNull(tu);
		// 通过username删除User对象
		us.delete(baseUser.getUsername());
		// 再次从表中取出User对象
		tu = us.load(baseUser.getUsername());
		// 删除后为空才正确
		//在service中加了用户名为"admin"时不删除的逻辑，所以会报错，这样就能在修改代码后快速的找出新代码带来的问题
		assertNull("删除的用户还存在",tu);
	}
	
	// 登录测试
	@Test
	public void testLogin() {
		// 加入User
		us.add(baseUser);
		int id = 1;
		String username = "admin";
		String password = "123";
		User tu = new User(id,username,password,"123");
		// 判断存进表的User对象和从表中拿出的User对象是否相同
		assertEquals(tu.getUsername(), baseUser.getUsername());
		assertEquals(tu.getPassword(), baseUser.getPassword());
	}
	
	// 用户名不存在测试
	@Test(expected = UserException.class)
	public void testNotExistsUserLogin() {
		// 向表中加入User
		us.add(baseUser);
		// 给一个不存在的用户名
		String username = "admin1";
		String password = "123";
		// 调用login方法，应该报UserException异常
		us.login(username, password);
	}
	
	// 密码错误测试
	@Test(expected = UserException.class)
	public void testPasswordErrorUserLogin() {
		// 向表中加入User
		us.add(baseUser);
		// 给一个不存在的用户名
		String username = "admin";
		String password = "1234";
		// 调用login方法，应该报UserException异常
		us.login(username, password);
	}
}
