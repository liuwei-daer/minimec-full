package com.allyes.minimec.domain.mapper.sys;

import com.allyes.minimec.domain.model.sys.TipInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TipInfoMapper extends Mapper<TipInfo> {

    List<TipInfo> selectBySearch(@Param("searchVal") String searchVal);

    List<TipInfo> selectByCode(@Param("tipCode") String tipCode);

}