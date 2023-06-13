package com.example.shardingtable;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shardingtable.entity.Dict;
import com.example.shardingtable.entity.Order;
import com.example.shardingtable.entity.OrderItem;
import com.example.shardingtable.entity.OrderVo;
import com.example.shardingtable.mapper.DictMapper;
import com.example.shardingtable.mapper.OrderItemMapper;
import com.example.shardingtable.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class ShardingTableApplicationTests {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private DictMapper dictMapper;

	/**
	 * 水平分片：插入数据测试
	 */
	@Test
	public void testInsertOrder(){
		Order order = new Order();
		order.setOrderNo("ATGUIGU001");
		order.setUserId(1L);
		order.setAmount(new BigDecimal(100));
		orderMapper.insert(order);
	}
	/**
	 * 水平分片：分库插入数据测试
	 */
	@Test
	public void testInsertOrderDatabaseStrategy(){

		for (long i = 0; i < 4; i++) {
			Order order = new Order();
			order.setOrderNo("ATGUIGU001");
			order.setUserId(i + 1);
			order.setAmount(new BigDecimal(100));
			orderMapper.insert(order);
		}

	}
	/**
	 * 水平分片：分表插入数据测试
	 */
	@Test
	public void testInsertOrderTableStrategy(){

		for (long i = 1; i < 5; i++) {

			Order order = new Order();
			order.setOrderNo("ATGUIGU" + i);
			order.setUserId(1L);
			order.setAmount(new BigDecimal(100));
			orderMapper.insert(order);
		}

		for (long i = 5; i < 9; i++) {

			Order order = new Order();
			order.setOrderNo("ATGUIGU" + i);
			order.setUserId(2L);
			order.setAmount(new BigDecimal(100));
			orderMapper.insert(order);
		}
	}
	/**
	 * 水平分片：查询所有记录
	 * 查询了两个数据源，每个数据源中使用UNION ALL连接两个表
	 */
	@Test
	public void testShardingSelectAll(){

		List<Order> orders = orderMapper.selectList(null);
		orders.forEach(System.out::println);
	}

	/**
	 * 水平分片：根据user_id查询记录
	 * 查询了一个数据源，每个数据源中使用UNION ALL连接两个表
	 */
	@Test
	public void testShardingSelectByUserId(){

		QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
		orderQueryWrapper.eq("user_id", 1L);
		List<Order> orders = orderMapper.selectList(orderQueryWrapper);
		orders.forEach(System.out::println);
	}

	/**
	 * 测试关联表插入
	 */
	@Test
	public void testInsertOrderAndOrderItem(){

		for (long i = 1; i < 3; i++) {

			Order order = new Order();
			order.setOrderNo("ATGUIGU" + i);
			order.setUserId(1L);
			orderMapper.insert(order);

			for (long j = 1; j < 3; j++) {
				OrderItem orderItem = new OrderItem();
				orderItem.setOrderNo("ATGUIGU" + i);
				orderItem.setUserId(1L);
				orderItem.setPrice(new BigDecimal(10));
				orderItem.setCount(2);
				orderItemMapper.insert(orderItem);
			}
		}

		for (long i = 5; i < 7; i++) {

			Order order = new Order();
			order.setOrderNo("ATGUIGU" + i);
			order.setUserId(2L);
			orderMapper.insert(order);

			for (long j = 1; j < 3; j++) {
				OrderItem orderItem = new OrderItem();
				orderItem.setOrderNo("ATGUIGU" + i);
				orderItem.setUserId(2L);
				orderItem.setPrice(new BigDecimal(1));
				orderItem.setCount(3);
				orderItemMapper.insert(orderItem);
			}
		}

	}

	/**
	 * 测试关联表查询
	 */
	@Test
	public void testGetOrderAmount(){

		List<OrderVo> orderAmountList = orderMapper.getOrderAmount();
		orderAmountList.forEach(System.out::println);
	}
	/**
	 * 广播表：每个服务器中的t_dict同时添加了新数据
	 */
	@Test
	public void testBroadcast(){

		Dict dict = new Dict();
		dict.setDictType("type1");
		dictMapper.insert(dict);
	}

	/**
	 * 查询操作，只从一个节点获取数据
	 * 随机负载均衡规则
	 */
	@Test
	public void testSelectBroadcast(){

		List<Dict> dicts = dictMapper.selectList(null);
		dicts.forEach(System.out::println);
	}
}
