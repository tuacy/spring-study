package com.tuacy.common.api.param;

import lombok.Data;

/**
 * @name: BaseParam
 * @author: tuacy.
 * @date: 2020/4/14.
 * @version: 1.0
 * @Description:
 */
@Data
public class BaseParam {
    private long userId;
    private String userName;
    private String token;
}
