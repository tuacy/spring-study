package com.tuacy.collect.mybatis.entity;

import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * 性别枚举类
 * @author wuyx
 * @version 1.0
 * @date 2020/5/1 23:53
 */
public enum ESex {

    /**
     * 男士
     */
    male(0),
    /**
     * 女士
     */
    female(1);

    private int value;

    ESex(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ESex toEnum(Integer value) {
        if (value == null) {
            return null;
        }
        for (ESex eItem : ESex.values()) {
            if (eItem.value == value) {
                return eItem;
            }
        }
        return null;
    }

    public static ESex toEnum(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        //循环输出 值
        for (ESex eItem : ESex.values()) {
            if (Objects.equals(String.valueOf(eItem.value), value)) {
                return eItem;
            }
        }
        return null;
    }
}
