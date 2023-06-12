package com.example.shardingtable.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shardingtable.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : cofe
 * @date : 2023-06-11
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
