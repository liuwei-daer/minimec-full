package com.allyes.minimec.admin.service.sys;

import com.allyes.minimec.domain.mapper.sys.UserRoleMapper;
import com.allyes.minimec.domain.model.sys.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuwei
 * @date 2018-03-10
 */
@Service
public class UserRoleService {


    @Autowired
    UserRoleMapper userRoleMapper;

    public void insertBatch(List<UserRole> list){
        userRoleMapper.insertBatch(list);
    }

    public void deleteByUserId(Integer userId){
        userRoleMapper.deleteByUserId(userId);
    }

    public List<Integer> selectByUserId(Integer userId){
        return userRoleMapper.selectByUserId(userId);
    }

}
