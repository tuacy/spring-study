package com.tuacy.collect.mybatis.entity.pojo;

import com.tuacy.collect.mybatis.entity.ESex;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/5/1 22:21
 */
public class User {

    private Long pkId;
    private String name;
    private String phone;
    /**
     * 性别枚举，这里会用到 EnumOrdinalTypeHandler 类型转换器
     */
    private ESex sex;

    public Long getPkId() {
        return pkId;
    }

    public void setPkId(Long pkId) {
        this.pkId = pkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ESex getSex() {
        return sex;
    }

    public void setSex(ESex sex) {
        this.sex = sex;
    }
}
