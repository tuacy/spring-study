package com.tuacy.elasticsearch.repository;

import com.tuacy.elasticsearch.entity.UserInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @name: IUserInfoRepository
 * @author: tuacy.
 * @date: 2020/3/30.
 * @version: 1.0
 */
public interface IUserInfoRepository extends ElasticsearchRepository<UserInfo, Long> {



}
