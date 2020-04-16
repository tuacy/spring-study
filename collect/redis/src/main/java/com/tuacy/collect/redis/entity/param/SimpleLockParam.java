package com.tuacy.collect.redis.entity.param;

import com.tuacy.common.api.param.BaseParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @version 1.0
 * @author: tuacy.
 * @date: 2020/4/16 21:13.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SimpleLockParam extends BaseParam {

    @NotBlank(message = "key不能为空")
    private String key;

}
