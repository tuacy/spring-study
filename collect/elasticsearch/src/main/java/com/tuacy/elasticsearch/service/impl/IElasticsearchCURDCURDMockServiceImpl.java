package com.tuacy.elasticsearch.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.tuacy.common.entity.PageEntity;
import com.tuacy.elasticsearch.entity.UserInfo;
import com.tuacy.elasticsearch.repository.IUserInfoRepository;
import com.tuacy.elasticsearch.service.IElasticsearchCURDMockService;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * @name: IElasticsearchMockServiceImpl
 * @author: tuacy.
 * @date: 2020/3/30.
 * @version: 1.0
 * @Description:
 */
@Service
public class IElasticsearchCURDCURDMockServiceImpl implements IElasticsearchCURDMockService {

    private IUserInfoRepository userInfoRepository;
    private TransportClient transportClient;

    @Autowired
    public void setUserInfoRepository(IUserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Autowired
    public void setTransportClient(TransportClient transportClient) {
        this.transportClient = transportClient;
    }

    /**
     * 记录增加
     */
    @Override
    public UserInfo insert(UserInfo userInfo) {
        Assert.notNull(userInfo, "需要创建的实例不能为null");
        return userInfoRepository.save(userInfo);
    }

    /**
     * 记录删除
     */
    @Override
    public void delete(String id) {
        Assert.notNull(id, "主键id不能为null");
        userInfoRepository.deleteById(Long.parseLong(id));
    }

    /**
     * 记录修改
     */
    @Override
    public UserInfo update(UserInfo userInfo) {
        Assert.notNull(userInfo, "需要创建的实例不能为null");
        Assert.notNull(userInfo.getId(), "主键id不能为null");
        return userInfoRepository.save(userInfo);
    }

    /**
     * 根据主键查找记录
     */
    @Override
    public UserInfo queryById(String id) {
        Assert.notNull(id, "主键id不能为null");
        Optional<UserInfo> optional = userInfoRepository.findById(Long.parseLong(id));
        return optional.orElse(null);
    }

    /**
     * 根据单个条件（用户名）查询
     */
    @Override
    public List<UserInfo> queryByName(String name) {
        Assert.notNull(name, "name不能为null");
        TermQueryBuilder termQuery = new TermQueryBuilder("name", name);
        Iterable<UserInfo> iterable = userInfoRepository.search(termQuery);
        if (iterable != null) {
            return Lists.newArrayList(iterable);
        }
        return null;
    }

    /**
     * 分页查询
     */
    @Override
    public PageEntity<UserInfo> pageQuery(int pageNo, int pageSize, String name) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchPhraseQuery("name", name))
                .withPageable(PageRequest.of(pageNo, pageSize))
                .build();
        Page<UserInfo> page = userInfoRepository.search(searchQuery);
        PageEntity<UserInfo> pageEntity = new PageEntity<>();
        pageEntity.setPageNo(pageNo);
        pageEntity.setPageSize(pageSize);
        if (page != null) {
            pageEntity.setPageCount(page.getTotalPages());
            pageEntity.setPageNo(pageNo);
            pageEntity.setPageSize(pageSize);
            pageEntity.setItemCount(page.getNumberOfElements());
            pageEntity.setData(page.getContent());
        }
        return pageEntity;
    }

    /**
     * 根据多个条件查询
     */
    @Override
    public List<UserInfo> queryMultiCondition(String name, int age) {
        Assert.notNull(name, "name不能为null");
        Preconditions.checkArgument(age > 0, "年龄不能<0");
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.must(QueryBuilders.termQuery("name", name));
        builder.must(QueryBuilders.matchPhraseQuery("age", age));
        //2.构建查询
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(builder);
        //生产NativeSearchQuery
        NativeSearchQuery query = nativeSearchQueryBuilder.build();
        Iterable<UserInfo> iterable = userInfoRepository.search(query);
        if (iterable != null) {
            return Lists.newArrayList(iterable);
        }
        return null;
    }
}
