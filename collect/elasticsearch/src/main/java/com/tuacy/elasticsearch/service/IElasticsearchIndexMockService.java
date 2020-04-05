package com.tuacy.elasticsearch.service;

/**
 * es 索引操作
 * @author wuyx
 * @version 1.0
 * @date 2020/4/5 22:27
 */
public interface IElasticsearchIndexMockService {

    /**
     * 创建索引
     * @param index 索引对应的名字
     * @return 创建索引是否成功
     */
    boolean indexCreate(String index);

    /**
     * 删除索引
     * @param index 索引名字
     * @return 删除索引是否成功
     */
    boolean indexDelete(String index);

    /**
     * 索引是否存在
     * @param index 索引名字
     * @return 索引是否存在
     */
    boolean indexExists(String index);


}
