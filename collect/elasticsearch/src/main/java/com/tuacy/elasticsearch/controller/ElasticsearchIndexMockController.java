package com.tuacy.elasticsearch.controller;

import com.tuacy.common.api.controller.BaseController;
import com.tuacy.common.api.response.Response;
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
public class ElasticsearchIndexMockController extends BaseController {

    private IElasticsearchIndexMockService elasticsearchIndexMockService;

    @Autowired
    public void setElasticsearchIndexMockService(IElasticsearchIndexMockService elasticsearchIndexMockService) {
        this.elasticsearchIndexMockService = elasticsearchIndexMockService;
    }

    @RequestMapping(value = "/crate", method = RequestMethod.POST)
    public Response<?> indexCreate(@RequestBody String index) {
        return setResult(elasticsearchIndexMockService.indexCreate(index));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Response<?> indexDelete(@RequestBody String index) {
        return setResult(elasticsearchIndexMockService.indexDelete(index));
    }

    @RequestMapping(value = "/exists", method = RequestMethod.POST)
    public Response<?> indexExists(@RequestBody String index) {
        return setResult(elasticsearchIndexMockService.indexExists(index));
    }

}
