package com.tuacy.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
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
@Document(indexName = "user", type = "docs")
public class UserInfo {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String name;

}
