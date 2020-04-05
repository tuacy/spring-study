package com.tuacy.common.entity.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 分页请求
 *
 * @author wuyx
 * @version 1.0
 * @date 2020/4/5 23:26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BasePageParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 6896802719286192606L;

    private int pageNo;
    private int pageSize;

}
