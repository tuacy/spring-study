package com.tuacy.elasticsearch.service.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Preconditions;
import com.tuacy.elasticsearch.service.IElasticsearchIndexMockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

/**
 * es 索引操作
 *
 * @author wuyx
 * @version 1.0
 * @date 2020/4/5 22:27
 */
@Service
public class IElasticsearchIndexMockServiceImpl implements IElasticsearchIndexMockService {

    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    public void setElasticsearchTemplate(ElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    /**
     * 创建索引
     *
     * @param index 索引对应的名字
     * @return 创建索引是否成功
     */
    @Override
    public boolean indexCreate(String index) {
        Preconditions.checkArgument(StrUtil.isEmpty(index), "index索引不能为null");
        if (!indexExists(index)) {
            return elasticsearchTemplate.createIndex(index);
        }
        return true;
    }

    /**
     * 删除索引
     *
     * @param index 索引名字
     * @return 删除索引是否成功
     */
    @Override
    public boolean indexDelete(String index) {
        Preconditions.checkArgument(StrUtil.isEmpty(index), "index索引不能为null");
        if (!indexExists(index)) {
            return elasticsearchTemplate.deleteIndex(index);
        }
        return true;
    }

    /**
     * 索引是否存在
     *
     * @param index 索引名字
     * @return 索引是否存在
     */
    @Override
    public boolean indexExists(String index) {
        Preconditions.checkArgument(StrUtil.isEmpty(index), "index索引不能为null");
        return elasticsearchTemplate.indexExists(index);
    }

}
