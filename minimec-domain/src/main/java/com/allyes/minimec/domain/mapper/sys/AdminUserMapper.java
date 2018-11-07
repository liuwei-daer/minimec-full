package com.allyes.minimec.domain.mapper.sys;

import com.allyes.minimec.domain.model.sys.AdminUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AdminUserMapper extends Mapper<AdminUser> {

    AdminUser selectByMobile(@Param("mobile") String phone);

    List<AdminUser> selectBySearch(@Param("search")String search, @Param("isAdmin") boolean isAdmin);

    int selectMobileIsExists(@Param("mobile") String mobile,@Param("id") Integer id);
}