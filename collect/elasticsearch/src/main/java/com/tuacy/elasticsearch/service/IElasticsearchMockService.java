package com.tuacy.elasticsearch.service;

import com.tuacy.elasticsearch.entity.UserInfo;

/**
 * @name: IElasticsearchMockService
 * @author: tuacy.
 * @date: 2020/3/30.
 * @version: 1.0
 * @Description:
 */
public interface IElasticsearchMockService {

    /**
     * 创建索引
     */
    boolean createIndex(String indexName);

    /**
     * 删除索引
     */
    boolean deleteIndex(String indexName);

    /**
     * 增加一条记录
     */
    UserInfo saveItem(UserInfo userInfo);

    /**
     * 查找所有记录
     */
    void findAll();

}
