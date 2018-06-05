package com.allyes.minimec.domain.mapper.sys;

import com.allyes.minimec.domain.dto.sys.OperatLogDto;
import com.allyes.minimec.domain.model.sys.OperatLog;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OperatLogMapper extends Mapper<OperatLog> {

    List<OperatLogDto> selectBySearch(OperatLogDto operatLogDto);
}