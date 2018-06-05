package com.allyes.minimec.domain.mapper.sys;

import com.allyes.minimec.domain.model.sys.UserRole;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserRoleMapper extends Mapper<UserRole> {

    void insertBatch(List<UserRole> list);

    void deleteByUserId(@Param("userId") Integer userId);

    void deleteByRoleId(@Param("roleId") Integer roleId);

    List<Integer> selectByUserId(@Param("userId") Integer userId);
}