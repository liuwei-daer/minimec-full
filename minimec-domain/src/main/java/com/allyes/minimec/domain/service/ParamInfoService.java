package com.allyes.minimec.domain.service;

import com.allyes.minimec.common.exception.BizException;
import com.allyes.minimec.domain.dto.BasePageDto;
import com.allyes.minimec.domain.mapper.sys.ParamInfoMapper;
import com.allyes.minimec.domain.model.sys.ParamInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * @author  liuwei
 * @date: 2018-03-10
 * 系统参数服务
 */
@Service
public class ParamInfoService {

    @Autowired
    private ParamInfoMapper paramInfoMapper;


    public PageInfo<ParamInfo> findByPage(BasePageDto basePageDto) {
        //分页
        if (!StringUtils.isEmpty(basePageDto.getPage()) && !StringUtils.isEmpty(basePageDto.getRows())) {
            PageHelper.startPage(basePageDto.getPage(), basePageDto.getRows());
        }

        List<ParamInfo> list = paramInfoMapper.selectBySearch(basePageDto.getSearchVal());
        PageInfo<ParamInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public Integer insert(ParamInfo record) throws BizException {
        int row = paramInfoMapper.insertSelective(record);
        if (row > 0) {
            int id = record.getId();
            return id;
        }
        throw new BizException();
    }


    public boolean updateById(ParamInfo record) {
        int row = paramInfoMapper.updateByPrimaryKeySelective(record);
        if (row > 0) {
            return true;
        }
        return false;
    }

    public boolean deleteById(Integer id) {
        int row = paramInfoMapper.deleteByPrimaryKey(id);
        if (row > 0) {
            return true;
        }
        return false;
    }

    public ParamInfo selectById(Integer id) {
        return paramInfoMapper.selectByPrimaryKey(id);
    }


    public ParamInfo getItByCode(String paramKey) {
        List<ParamInfo> list = paramInfoMapper.selectByParamKey(paramKey);
        ParamInfo paramInfo = null;
        if (list != null && !list.isEmpty()) {
            paramInfo = list.get(0);
        }
        return paramInfo;
    }

    public String getParamValue(String paramKey){
        return paramInfoMapper.selectParamValue(paramKey);
    }

    public Integer getIntValue(String paramKey){
        String pv = paramInfoMapper.selectParamValue(paramKey);
        Integer pvInt=Integer.parseInt(pv.trim());
        return pvInt;
    }

    /**
     * 获取所有的消息提示信息
     *
     * @return
     */
    public List<ParamInfo> getAll() {
        List<ParamInfo> list = paramInfoMapper.selectAll();
        return list;
    }
}
