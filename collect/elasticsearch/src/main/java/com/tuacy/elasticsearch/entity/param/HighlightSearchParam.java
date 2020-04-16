package com.tuacy.elasticsearch.entity.param;

import com.tuacy.common.api.param.BasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/4/6 0:06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HighlightSearchParam extends BasePageParam {

    private static final long serialVersionUID = -5086992628384103835L;
    private String name;

}
