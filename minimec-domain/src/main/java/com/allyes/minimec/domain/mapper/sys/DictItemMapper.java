package com.allyes.minimec.domain.mapper.sys;

import com.allyes.minimec.domain.model.sys.DictItem;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DictItemMapper extends Mapper<DictItem> {

    List<DictItem> selectBySearch(@Param("searchVal")String searchVal);


    List<DictItem> selectByDictId(@Param("dictId") Integer dictId);

}