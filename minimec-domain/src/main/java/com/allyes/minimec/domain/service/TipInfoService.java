package com.allyes.minimec.domain.service;

import com.allyes.minimec.domain.mapper.sys.TipInfoMapper;
import com.allyes.minimec.domain.model.sys.TipInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * @author liuwei
 * @date 2018-03-10
 * 消息提示服务
 */
@Service
public class TipInfoService {

    @Autowired
    private TipInfoMapper tipInfoMapper;


    public PageInfo<TipInfo> findByPage(Integer page, Integer rows, String searchVal) {
        //分页
        if (!StringUtils.isEmpty(page) && !StringUtils.isEmpty(rows)) {
            PageHelper.startPage(page, rows);
        }

        List<TipInfo> list = tipInfoMapper.selectBySearch(searchVal);
        PageInfo<TipInfo> pageInfo = new PageInfo<TipInfo>(list);
        return pageInfo;
    }

    public Integer insertIt(TipInfo record) throws Exception {
        int row = tipInfoMapper.insertSelective(record);
        if (row > 0) {
            int id = record.getId();
            return id;
        }
        throw new Exception();
    }


    public boolean updateById(TipInfo record) {
        int row = tipInfoMapper.updateByPrimaryKeySelective(record);
        if (row > 0) {
            return true;
        }
        return false;
    }

    public boolean deleteById(Integer id) {
        int row = tipInfoMapper.deleteByPrimaryKey(id);
        if (row > 0) {
            return true;
        }
        return false;
    }

    public TipInfo selectById(Integer id) {
        return tipInfoMapper.selectByPrimaryKey(id);
    }


    public TipInfo getItByCode(String tipCode) {
        List<TipInfo> list = tipInfoMapper.selectByCode(tipCode);
        TipInfo tipInfo = null;
        if (list != null && !list.isEmpty()) {
            tipInfo = list.get(0);
        }
        return tipInfo;
    }

    /**
     * 获取所有的消息提示信息
     *
     * @return
     */
    public List<TipInfo> getAll() {
        List<TipInfo> list = tipInfoMapper.selectAll();
        return list;
    }
}
