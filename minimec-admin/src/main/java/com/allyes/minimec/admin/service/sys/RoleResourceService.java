package com.allyes.minimec.admin.service.sys;

import com.allyes.minimec.domain.mapper.sys.RoleResourceMapper;
import com.allyes.minimec.domain.model.sys.RoleResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuwei
 * @date 2018-03-10
 */
@Slf4j
@Service
public class RoleResourceService {

    @Autowired
    RoleResourceMapper roleResourceMapper;

    public void delByResourceId(final Integer resourceId){
        roleResourceMapper.delByResourceId(resourceId);
    }

    public void insert(RoleResource record){
        roleResourceMapper.insert(record);
    }

    public List<Integer> selectIdByRoleId(final Integer roleId){
        return roleResourceMapper.selectIdByRoleId(roleId);
    }
}
