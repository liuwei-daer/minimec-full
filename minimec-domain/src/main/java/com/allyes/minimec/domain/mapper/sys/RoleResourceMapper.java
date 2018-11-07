package com.allyes.minimec.domain.mapper.sys;

import com.allyes.minimec.domain.model.sys.RoleResource;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleResourceMapper extends Mapper<RoleResource> {

    int delByResourceId(@Param("resourceId") Integer resourceId);

    int delByRoleId(@Param("roleId") Integer roleId);

    List<Integer> selectIdByRoleId(@Param("roleId") Integer roleId);

    void insertBatch(List<RoleResource> list);
}