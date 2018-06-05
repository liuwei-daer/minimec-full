package com.allyes.minimec.domain.mapper.sys;

import com.allyes.minimec.domain.model.sys.ParamInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ParamInfoMapper extends Mapper<ParamInfo> {

    List<ParamInfo> selectBySearch(@Param("searchVal")String searchVal);

    List<ParamInfo> selectByParamKey(@Param("paramKey")String paramKey);

    @Select("SELECT t.param_value FROM `param_info` t WHERE t.param_key=#{paramKey} LIMIT 1")
    String selectParamValue(@Param("paramKey")String paramKey);

}