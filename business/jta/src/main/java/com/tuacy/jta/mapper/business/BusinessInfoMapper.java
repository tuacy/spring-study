package com.tuacy.jta.mapper.business;

import com.tuacy.jta.entity.model.business.BusinessInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @name: BusinessInfoMapper
 * @author: tuacy.
 * @date: 2020/1/3.
 * @version: 1.0
 * @Description:
 */
public interface BusinessInfoMapper {

    BusinessInfo selectByPkId(@Param("pkId") String pkId);

    int insert(@Param("pkId") String pkId, @Param("value") String value);

}
