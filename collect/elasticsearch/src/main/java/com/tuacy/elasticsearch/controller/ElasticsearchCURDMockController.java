package com.tuacy.elasticsearch.controller;

import com.tuacy.common.api.controller.BaseController;
import com.tuacy.common.api.response.PageResponse;
import com.tuacy.common.api.response.Response;
import com.tuacy.elasticsearch.entity.UserInfo;
import com.tuacy.elasticsearch.entity.param.HighlightSearchParam;
import com.tuacy.elasticsearch.entity.param.UserMultiConditionSearchParam;
import com.tuacy.elasticsearch.entity.param.UserPageSearchParam;
import com.tuacy.elasticsearch.service.IElasticsearchCURDMockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ES 增删改查操作
 *
 * @name: ElasticsearchCURDMockController
 * @author: tuacy.
 * @date: 2020/3/30.
 * @version: 1.0
 * @Description:
 */
@RestController
@RequestMapping("/v1/elasticsearch/CURD")
public class ElasticsearchCURDMockController extends BaseController {

    private IElasticsearchCURDMockService elasticsearchMockService;

    @Autowired
    public void setElasticsearchMockService(IElasticsearchCURDMockService elasticsearchMockService) {
        this.elasticsearchMockService = elasticsearchMockService;
    }

    /**
     * 插入单个记录
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Response<?> insert(@RequestBody UserInfo userInfo) {
        return setResult(elasticsearchMockService.insert(userInfo));
    }

    /**
     * 插入多条记录
     */
    @RequestMapping(value = "/insertBatch", method = RequestMethod.POST)
    public Response<?> insertBatch(@RequestBody List<UserInfo> userList) {
        return setResult(elasticsearchMockService.insertBatch(userList));
    }

    /**
     * 删除单个记录
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Response<?> delete(@RequestBody String id) {
        elasticsearchMockService.delete(id);
        return setResult();
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    public Response<?> deleteBatch(@RequestBody List<String> idList) {
        elasticsearchMockService.deleteBatch(idList);
        return setResult();
    }

    /**
     * 修改记录
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response<?> update(@RequestBody UserInfo userInfo) {
        elasticsearchMockService.update(userInfo);
        return setResult();
    }

    /**
     * 批量修改记录
     */
    @RequestMapping(value = "/updateBatch", method = RequestMethod.POST)
    public Response<?> updateBatch(@RequestBody List<UserInfo> userInfoList) {
        elasticsearchMockService.updateBatch(userInfoList);
        return setResult();
    }

    /**
     * 根据主键查询
     */
    @RequestMapping(value = "/searchById", method = RequestMethod.POST)
    public Response<?> searchById(@RequestBody String id) {
        return setResult(elasticsearchMockService.queryById(id));
    }

    /**
     * 通过某个字段去查询
     */
    @RequestMapping(value = "/searchByName", method = RequestMethod.POST)
    public Response<?> searchByName(@RequestBody String name) {
        return setResult(elasticsearchMockService.queryByName(name));
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/searchByPage", method = RequestMethod.POST)
    public PageResponse<?> searchByPage(@RequestBody UserPageSearchParam param) {
        return setPageResult(elasticsearchMockService.pageQuery(param.getPageNo(), param.getPageSize(), param.getName()));
    }

    @RequestMapping(value = "/searchMultiCondition", method = RequestMethod.POST)
    public Response<?> searchMultiCondition(@RequestBody UserMultiConditionSearchParam param) {
        return setResult(elasticsearchMockService.queryMultiCondition(param.getName(), param.getAge()));
    }

    /**
     * 高亮查询
     */
    @RequestMapping(value = "/searchHighlight", method = RequestMethod.POST)
    public Response<?> searchHighlight(@RequestBody HighlightSearchParam param) {
        return setPageResult(elasticsearchMockService.highlightQuery(param.getPageNo(), param.getPageSize(), param.getName()));
    }
}
