package com.allyes.minimec.domain.mapper.sys;

import com.allyes.minimec.domain.model.sys.Resource;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ResourceMapper extends Mapper<Resource> {

    List<Resource> selectAllByUrlNotNull();

    List<Resource> selectAll();

    List<Resource> selectMenuByRoleList(@Param("roleIds")String roleIds);

    List<Resource> selectByRoleId(@Param("roleId") Integer roleId);

    String selectParentIdsById(@Param("pid") Integer pid);

    List<Resource> selectByRoleList(List<Integer> list);
}