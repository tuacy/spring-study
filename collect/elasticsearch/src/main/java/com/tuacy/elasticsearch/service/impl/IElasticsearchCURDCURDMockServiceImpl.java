package com.tuacy.elasticsearch.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.tuacy.common.entity.PageEntity;
import com.tuacy.elasticsearch.constant.EsConstant;
import com.tuacy.elasticsearch.entity.UserInfo;
import com.tuacy.elasticsearch.repository.HighlightResultMapper;
import com.tuacy.elasticsearch.repository.IUserInfoRepository;
import com.tuacy.elasticsearch.service.IElasticsearchCURDMockService;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Elasticsearch 文档操作
 * @name: IElasticsearchMockServiceImpl
 * @author: tuacy.
 * @date: 2020/3/30.
 * @version: 1.0
 * @Description:
 */
@Service
public class IElasticsearchCURDCURDMockServiceImpl implements IElasticsearchCURDMockService {

    private IUserInfoRepository userInfoRepository;
    private ElasticsearchTemplate elasticsearchTemplate;
    private HighlightResultMapper highlightResultMapper;

    @Autowired
    public void setUserInfoRepository(IUserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Autowired
    public void setElasticsearchTemplate(ElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Autowired
    public void setHighlightResultMapper(HighlightResultMapper highlightResultMapper) {
        this.highlightResultMapper = highlightResultMapper;
    }

    /**
     * 插入单挑记录
     */
    @Override
    public UserInfo insert(UserInfo userInfo) {
        Assert.notNull(userInfo, "需要创建的实例不能为null");
        return userInfoRepository.save(userInfo);
    }

    /**
     * 批量插入
     */
    @Override
    public Iterable<UserInfo> insertBatch(List<UserInfo> userInfoList) {
        if (CollectionUtils.isEmpty(userInfoList)) {
            return null;
        }
        return Lists.newArrayList(userInfoRepository.saveAll(userInfoList));
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
     * 批量删除
     */
    @Override
    public void deleteBatch(List<String> idList) {
        if (CollectionUtil.isEmpty(idList)) {
            return;
        }
        List<UserInfo> userInfoList = idList.stream().map(item -> {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(Long.parseLong(item));
            return userInfo;
        }).collect(Collectors.toList());

        userInfoRepository.deleteAll(userInfoList);
    }

    /**
     * 记录修改
     */
    @Override
    public boolean update(UserInfo userInfo) {
        Assert.notNull(userInfo, "需要创建的实例不能为null");
        Assert.notNull(userInfo.getId(), "主键id不能为null");

        String id = userInfo.getId().toString();

        userInfo.setId(null);
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.source(JSON.toJSONString(userInfo), XContentType.JSON);

        UpdateQuery updateQuery = new UpdateQueryBuilder()
                .withId(id)
                .withClass(UserInfo.class)
                .withIndexRequest(indexRequest)
                .build();

        UpdateResponse response = this.elasticsearchTemplate.update(updateQuery);
        return DocWriteResponse.Result.UPDATED == response.getResult();
    }

    /**
     * 批量修改记录
     */
    @Override
    public boolean updateBatch(List<UserInfo> userInfoList) {
        if (CollectionUtil.isEmpty(userInfoList)) {
            return true;
        }
        List<UpdateQuery> updateQueryList = new ArrayList<>();
        for (UserInfo userItem : userInfoList) {
            String id = userItem.getId().toString();
            userItem.setId(null);
            IndexRequest indexRequest = new IndexRequest();
            indexRequest.source(JSON.toJSONString(userItem), XContentType.JSON);

            UpdateQuery updateQuery = new UpdateQueryBuilder()
                    .withId(id)
                    .withClass(UserInfo.class)
                    .withIndexRequest(indexRequest)
                    .build();
            updateQueryList.add(updateQuery);
        }
        elasticsearchTemplate.bulkUpdate(updateQueryList, BulkOptions.defaultOptions());
        return true;
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
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termQuery("name", name)) // 精确查询
                .build();
        Iterable<UserInfo> iterable = userInfoRepository.search(searchQuery);
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

    /**
     * 高亮查询
     */
    @Override
    public PageEntity<UserInfo> highlightQuery(int pageNo, int pageSize, String name) {
        // 高亮配置
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        highlightBuilder.field("name");
        // 分页配置
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        // 查询条件配置
        TermQueryBuilder builder = QueryBuilders.termQuery("name", name);
        // 构建查询
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder()
                .withQuery(builder)
                .withHighlightBuilder(highlightBuilder)
                .withPageable(pageable);

        Page<UserInfo> pageResult = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), UserInfo.class, highlightResultMapper);
        PageEntity<UserInfo> pageEntity = new PageEntity<>();
        pageEntity.setPageNo(pageNo);
        pageEntity.setPageSize(pageSize);
        if (pageResult != null) {
            pageEntity.setPageCount(pageResult.getTotalPages());
            pageEntity.setPageNo(pageNo);
            pageEntity.setPageSize(pageSize);
            pageEntity.setItemCount(pageResult.getNumberOfElements());
            pageEntity.setData(pageResult.getContent());
        }
        return pageEntity;
    }
}
