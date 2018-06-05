package com.allyes.minimec.admin.service.sys;

import com.allyes.minimec.domain.dto.sys.OperatLogDto;
import com.allyes.minimec.domain.mapper.sys.OperatLogMapper;
import com.allyes.minimec.domain.model.sys.OperatLog;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author liuwei
 * @date 2018-03-10
 */
@Service
public class OperatLogService {

    @Autowired
    private OperatLogMapper operatLogMapper;

    public int insertSelective(OperatLog record) {
        return operatLogMapper.insertSelective(record);
    }


    /**
     * 分页查询系统日志列表
     * @param operatLogDto
     * return
     *
     * */
    public PageInfo<OperatLogDto> findByPage(OperatLogDto operatLogDto){
        //分页
        if (!StringUtils.isEmpty(operatLogDto.getPage()) && !StringUtils.isEmpty(operatLogDto.getRows())) {
            PageHelper.startPage(operatLogDto.getPage(), operatLogDto.getRows());
        }
        List<OperatLogDto> list = operatLogMapper.selectBySearch(operatLogDto);
        PageInfo<OperatLogDto> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

}
