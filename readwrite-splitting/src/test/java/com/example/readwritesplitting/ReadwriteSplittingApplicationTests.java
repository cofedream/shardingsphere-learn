package com.example.readwritesplitting;

import com.example.readwritesplitting.entity.User;
import com.example.readwritesplitting.mapper.UserMapper;
import org.bouncycastle.math.Primes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class ReadwriteSplittingApplicationTests {
	@Autowired
	private UserMapper userMapper;

	/**
	 * 写入数据的测试
	 */
	@Test
	public void testInsert() {
		User user = new User();
		user.setUname("张三丰");
		userMapper.insert(user);
	}

	/**
	 * 事务测试
	 */
	@Transactional//开启事务。单元测试开启事务测试，在结束回进行回滚
	@Test
	public void testTrans() {
		User user = new User();
		user.setUname("铁锤");
		userMapper.insert(user);
		List<User> users = userMapper.selectList(null);
		System.out.println(users.size());
	}
	/**
	 * 读数据测试
	 */
	@Test
	public void testSelectAll(){
		List<User> users1 = userMapper.selectList(null);
		users1.forEach(System.out::println);
		List<User> users2 = userMapper.selectList(null);//执行第二次测试负载均衡
		users2.forEach(System.out::println);
	}
}
