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
     * 插入单挑记录
     */
    UserInfo insert(UserInfo userInfo);

    /**
     * 批量插入
     */
    Iterable<UserInfo> insertBatch(List<UserInfo> userInfoList);

    /**
     * 删除单个记录
     */
    void delete(String id);

    /**
     * 批量删除
     */
    void deleteBatch(List<String> idList);

    /**
     * 修改单个记录
     */
    boolean update(UserInfo userInfo);

    /**
     * 批量修改记录
     */
    boolean updateBatch(List<UserInfo> userInfoList);

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

    /**
     * 高亮查询
     */
    PageEntity<UserInfo> highlightQuery(int pageNo, int pageSize, String name);

}
