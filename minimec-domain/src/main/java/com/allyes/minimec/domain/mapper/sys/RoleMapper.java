package com.allyes.minimec.domain.mapper.sys;

import com.allyes.minimec.domain.model.sys.Role;
import com.allyes.minimec.domain.model.sys.UserRole;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleMapper extends Mapper<Role> {

    List<Role> selectByUserId(@Param("userId") Integer userId);

    List<Role> selectBySearch(@Param("searchVal") String searchVal);

    int selectIsExistRole(@Param("id") Integer id, @Param("roleName") String roleName);

}