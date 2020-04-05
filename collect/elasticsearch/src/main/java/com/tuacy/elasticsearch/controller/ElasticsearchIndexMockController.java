package com.tuacy.elasticsearch.controller;

import com.tuacy.common.entity.ApiBaseController;
import com.tuacy.common.entity.response.ApiResponse;
import com.tuacy.elasticsearch.service.IElasticsearchIndexMockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * ES 索引的操作
 *
 * @author wuyx
 * @version 1.0
 * @date 2020/4/5 22:24
 */
@RestController
@RequestMapping("/v1/elasticsearch/index")
public class ElasticsearchIndexMockController extends ApiBaseController {

    private IElasticsearchIndexMockService elasticsearchIndexMockService;

    @Autowired
    public void setElasticsearchIndexMockService(IElasticsearchIndexMockService elasticsearchIndexMockService) {
        this.elasticsearchIndexMockService = elasticsearchIndexMockService;
    }

    @RequestMapping(value = "/crate", method = RequestMethod.POST)
    public ApiResponse<?> indexCreate(@RequestBody String index) {
        return setResult(elasticsearchIndexMockService.indexCreate(index));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ApiResponse<?> indexDelete(@RequestBody String index) {
        return setResult(elasticsearchIndexMockService.indexDelete(index));
    }

    @RequestMapping(value = "/exists", method = RequestMethod.POST)
    public ApiResponse<?> indexExists(@RequestBody String index) {
        return setResult(elasticsearchIndexMockService.indexExists(index));
    }

}
