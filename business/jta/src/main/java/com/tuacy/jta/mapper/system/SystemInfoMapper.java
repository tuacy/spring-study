package com.tuacy.jta.mapper.system;

import com.tuacy.jta.entity.model.system.SystemInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @name: BusinessInfoMapper
 * @author: tuacy.
 * @date: 2020/1/3.
 * @version: 1.0
 * @Description:
 */
public interface SystemInfoMapper {

    SystemInfo selectByPkId(@Param("pkId") String pkId);

}
