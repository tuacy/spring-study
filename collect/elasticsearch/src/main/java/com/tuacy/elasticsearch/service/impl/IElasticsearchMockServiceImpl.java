package com.tuacy.elasticsearch.service.impl;

import com.tuacy.elasticsearch.repository.IUserInfoRepository;
import com.tuacy.elasticsearch.entity.UserInfo;
import com.tuacy.elasticsearch.service.IElasticsearchMockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

/**
 * @name: IElasticsearchMockServiceImpl
 * @author: tuacy.
 * @date: 2020/3/30.
 * @version: 1.0
 * @Description:
 */
@Service
public class IElasticsearchMockServiceImpl implements IElasticsearchMockService {

    private ElasticsearchTemplate elasticsearchTemplate;
    private IUserInfoRepository userInfoRepository;

    @Autowired
    public void setElasticsearchTemplate(ElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Autowired
    public void setUserInfoRepository(IUserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }


    @Override
    public boolean createIndex(String indexName) {
        return elasticsearchTemplate.createIndex(indexName);
    }

    @Override
    public boolean deleteIndex(String indexName) {
        return elasticsearchTemplate.deleteIndex(indexName);
    }

    @Override
    public UserInfo saveItem(UserInfo userInfo) {
       return userInfoRepository.save(userInfo);
    }

    @Override
    public void findAll() {
        Iterable<UserInfo> userInfoIterable = userInfoRepository.findAll();
        System.out.println("aaaaaaaaaaaa");
    }
}
