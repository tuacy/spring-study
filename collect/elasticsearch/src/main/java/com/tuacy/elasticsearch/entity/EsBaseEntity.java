package com.tuacy.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体
 *
 * @author wuyx
 * @version 1.0
 * @date 2020/4/5 21:01
 */
@Data
public class EsBaseEntity implements Serializable {
    private static final long serialVersionUID = 1969971408867646117L;

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Date crateTime;

    /**
     * 修改时间
     */
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Date updateTime;

    public Date getCrateTime() {
        if (crateTime != null) {
            return crateTime;
        }
        return new Date();
    }

    public Date getUpdateTime() {
        if (updateTime != null) {
            return updateTime;
        }
        return new Date();
    }
}
