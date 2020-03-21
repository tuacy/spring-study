package com.tuacy.common.entity;

import lombok.Data;

import java.util.List;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 21:56
 */
@Data
public class ResponsePageResult<T> {

    private int status;
    private String msg;
    private int totalCount;
    private int pageIndex;
    private int pageCount;
    private List<T> data;

}
