package com.tuacy.elasticsearch.service;

import com.tuacy.common.entity.PageEntity;
import com.tuacy.elasticsearch.entity.UserInfo;

import java.util.List;

/**
 * 多条件查询
 * https://blog.csdn.net/Z__Sheng/article/details/100013663
 *
 * @name: IElasticsearchMockService
 * @author: tuacy.
 * @date: 2020/3/30.
 * @version: 1.0
 * @Description:
 */
public interface IElasticsearchCURDMockService {

    /**
     * 记录增加
     */
    UserInfo insert(UserInfo userInfo);

    /**
     * 记录删除
     */
    void delete(String id);

    /**
     * 记录修改
     */
    UserInfo update(UserInfo userInfo);

    /**
     * 根据主键查找记录
     */
    UserInfo queryById(String id);

    /**
     * 根据单个条件（用户名）查询
     */
    List<UserInfo> queryByName(String name);

    /**
     * 分页查询
     */
    PageEntity<UserInfo> pageQuery(int pageNo, int pageSize, String name);

    /**
     * 多条件查询
     */
    List<UserInfo> queryMultiCondition(String name, int age);

}
