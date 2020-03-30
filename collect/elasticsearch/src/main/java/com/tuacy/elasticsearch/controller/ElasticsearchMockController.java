package com.tuacy.elasticsearch.controller;

import com.tuacy.common.entity.ApiBaseController;
import com.tuacy.common.entity.ApiResponse;
import com.tuacy.elasticsearch.entity.UserInfo;
import com.tuacy.elasticsearch.service.IElasticsearchMockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @name: IndexMockController
 * @author: tuacy.
 * @date: 2020/3/30.
 * @version: 1.0
 * @Description:
 */
@RestController
@RequestMapping("/v1/elasticsearch")
public class ElasticsearchMockController extends ApiBaseController {

    private IElasticsearchMockService elasticsearchMockService;

    @Autowired
    public void setElasticsearchMockService(IElasticsearchMockService elasticsearchMockService) {
        this.elasticsearchMockService = elasticsearchMockService;
    }

    @RequestMapping(value = "/addIndex", method = RequestMethod.POST)
    public ApiResponse indexAdd(@RequestBody String indexName) {
        return setResult(elasticsearchMockService.createIndex(indexName));
    }

    @RequestMapping(value = "/deleteIndex", method = RequestMethod.POST)
    public ApiResponse indexDelete(@RequestBody String indexName) {
        return setResult(elasticsearchMockService.deleteIndex(indexName));
    }

    @RequestMapping(value = "/addItem", method = RequestMethod.POST)
    public ApiResponse addItem(@RequestBody UserInfo userInfo) {
        return setResult(elasticsearchMockService.saveItem(userInfo));
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public ApiResponse findAll() {
        elasticsearchMockService.findAll();
        return setResult("ok");
    }
}
