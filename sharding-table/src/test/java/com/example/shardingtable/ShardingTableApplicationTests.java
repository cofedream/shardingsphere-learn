package com.example.shardingtable;

import com.example.shardingtable.entity.Order;
import com.example.shardingtable.entity.User;
import com.example.shardingtable.mapper.OrderMapper;
import com.example.shardingtable.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class ShardingTableApplicationTests {
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private OrderMapper orderMapper;

	/**
	 * 垂直分片：插入数据测试
	 */
	@Test
	public void testInsertOrderAndUser(){

		User user = new User();
		user.setUname("强哥");
		userMapper.insert(user);

		Order order = new Order();
		order.setOrderNo("ATGUIGU001");
		order.setUserId(user.getId());
		order.setAmount(new BigDecimal(100));
		orderMapper.insert(order);

	}

	/**
	 * 垂直分片：查询数据测试
	 */
	@Test
	public void testSelectFromOrderAndUser(){
		User user = userMapper.selectById(1L);
		Order order = orderMapper.selectById(1L);
	}
}
