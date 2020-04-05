package com.tuacy.elasticsearch.entity;

import com.tuacy.elasticsearch.constant.EsConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @name: UserInfo
 * @author: tuacy.
 * @date: 2020/3/30.
 * @version: 1.0
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Document(indexName = EsConstant.INDEX_NAME.STUDY, type = "user")
public class UserInfo extends EsBaseEntity {
    private static final long serialVersionUID = 1814417843616675054L;

    /**
     * 姓名
     */
    @Field(type = FieldType.Text)
    private String name;

    /**
     * 年龄
     */
    @Field()
    private short age;

    /**
     * 性别
     */
    @Field()
    private byte sex;

    /**
     * 电话号码
     */
    @Field()
    private String phone;



}
