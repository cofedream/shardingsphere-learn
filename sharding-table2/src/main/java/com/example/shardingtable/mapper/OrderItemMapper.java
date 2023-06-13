package com.example.shardingtable.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shardingtable.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : cofe
 * @date : 2023-06-12
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {

}