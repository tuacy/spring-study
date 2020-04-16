package com.tuacy.elasticsearch.entity.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/4/6 0:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserMultiConditionSearchParam implements Serializable {
    private static final long serialVersionUID = 5563663280283085281L;

    private String name;
    private int age;
}
