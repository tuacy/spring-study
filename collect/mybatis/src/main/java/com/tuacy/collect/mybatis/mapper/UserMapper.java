package com.tuacy.collect.mybatis.mapper;

import com.tuacy.collect.mybatis.entity.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/5/1 22:26
 */
public interface UserMapper {

    List<User> listUser();

    User getUserByName(@Param("name") String name);

}
