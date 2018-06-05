package com.allyes.minimec.domain.service;

import com.allyes.minimec.domain.dto.BasePageDto;
import com.allyes.minimec.domain.mapper.sys.DictItemMapper;
import com.allyes.minimec.domain.model.sys.DictItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author liuwei
 * @date 2018-03-14
 */
@Service
public class DictItemService {

    @Autowired
    private DictItemMapper dictItemMapper;


    public PageInfo<DictItem> findByPage(BasePageDto basePageDto) {
        //分页
        if (!StringUtils.isEmpty(basePageDto.getPage()) && !StringUtils.isEmpty(basePageDto.getRows())) {
            PageHelper.startPage(basePageDto.getPage(), basePageDto.getRows());
        }

        List<DictItem> list = dictItemMapper.selectBySearch(basePageDto.getSearchVal());
        PageInfo<DictItem> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public Integer insertSelective(DictItem record) throws Exception {
        int row = dictItemMapper.insertSelective(record);
        if (row > 0) {
            int id = record.getId();
            return id;
        }
        throw new Exception();
    }


    public boolean updateById(DictItem record) {
        int row = dictItemMapper.updateByPrimaryKeySelective(record);
        if (row > 0) {
            return true;
        }
        return false;
    }

    public boolean deleteById(Integer id) {
        int row = dictItemMapper.deleteByPrimaryKey(id);
        if (row > 0) {
            return true;
        }
        return false;
    }

    public DictItem selectById(Integer id) {
        return dictItemMapper.selectByPrimaryKey(id);
    }


    /**
     * 获取所有的消息提示信息
     *
     * @return
     */
    public List<DictItem> selectAll() {
        List<DictItem> list = dictItemMapper.selectAll();
        return list;
    }

    /**
     * 获取所有的消息提示信息
     *
     * @return
     */
    public List<DictItem> selectByDictId(Integer dectId) {
        List<DictItem> list = dictItemMapper.selectByDictId(dectId);
        return list;
    }
}
