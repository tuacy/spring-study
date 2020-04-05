package com.tuacy.elasticsearch.entity.param;

import com.tuacy.common.entity.param.BaseParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/4/6 0:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserMultiConditionSearchParam extends BaseParam {
    private static final long serialVersionUID = 5563663280283085281L;

    private String name;
    private int age;
}
