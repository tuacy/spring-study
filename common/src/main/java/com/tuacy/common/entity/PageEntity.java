package com.tuacy.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页实体的封装
 * @author wuyx
 * @version 1.0
 * @date 2020/4/5 23:02
 */
@Data
public class PageEntity<T> implements Serializable {

    private static final long serialVersionUID = 5524893713874295046L;
    private int pageNo;
    private int pageSize;
    private int itemCount;
    private int pageCount;
    private List<T> data;

}
