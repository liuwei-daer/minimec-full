package com.allyes.minimec.admin.service.sys;

import com.allyes.minimec.common.constant.Constants;
import com.allyes.minimec.domain.dto.sys.AdminUserDto;
import com.allyes.minimec.domain.mapper.sys.AdminUserMapper;
import com.allyes.minimec.domain.model.sys.AdminUser;
import com.allyes.minimec.domain.model.sys.UserRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuwei
 * @date 2018-03-10
 */
@Slf4j
@Service
public class AdminUserService {

    @Autowired
    AdminUserMapper adminUserMapper;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public AdminUser selectByMobile(String mobile){
        return adminUserMapper.selectByMobile(mobile);
    }

    public List<AdminUser> findAll(){
        return adminUserMapper.selectByExample(null);
    }

    public PageInfo<AdminUser> findByPage(AdminUserDto adminUserDto, boolean isAdmin){
        if (!StringUtils.isEmpty(adminUserDto.getPage()) && !StringUtils.isEmpty(adminUserDto.getRows())) {
            PageHelper.startPage(adminUserDto.getPage(), adminUserDto.getRows());
        }
        List<AdminUser> list = adminUserMapper.selectBySearch(adminUserDto.getSearchVal(),isAdmin);
        PageInfo<AdminUser> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public AdminUserDto selectById(final int id){
        AdminUser sysAdminUser= adminUserMapper.selectByPrimaryKey(id);
        sysAdminUser.setSalt("");
        sysAdminUser.setPassword("");
        AdminUserDto sysAdminUserDto = new AdminUserDto();
        BeanUtils.copyProperties(sysAdminUser,sysAdminUserDto);
        sysAdminUserDto.setRoles(userRoleService.selectByUserId(id));
        return sysAdminUserDto;
    }

    @Transactional
    public void addAdminUser(AdminUserDto adminUserDto){
        //获取盐值，生成用户密码
        String salt = BCrypt.gensalt();
        String password = adminUserDto.getPassword();
        adminUserDto.setPassword(BCrypt.hashpw(password , salt));
        adminUserDto.setSalt(salt);
        //保存用户基本账号基本信息
        AdminUser adminUser = new AdminUser();
        BeanUtils.copyProperties(adminUserDto,adminUser);
        adminUser.setUserName(adminUser.getMobile().toString());
        adminUserMapper.insertSelective(adminUser);
        //添加用户角色关联关系信息
        userRoleService.insertBatch(getSysUserRoleList(adminUserDto.getRoles(),adminUser.getId()));
    }

    @Transactional
    public void updateAdminUser(AdminUserDto adminUserDto){
        AdminUser adminUser = new AdminUser();
        BeanUtils.copyProperties(adminUserDto,adminUser);
        adminUser.setUserName(adminUser.getMobile());
        adminUserMapper.updateByPrimaryKeySelective(adminUser);

        //清除用户角色关联关系
        userRoleService.deleteByUserId(adminUserDto.getId());

        //添加用户角色关联关系信息
        userRoleService.insertBatch(getSysUserRoleList(adminUserDto.getRoles(),adminUser.getId()));

        //删除用户redis中权限缓存
        stringRedisTemplate.delete(Constants.USER_PERMS + adminUserDto.getMobile());
    }

    /**
     * 获取系统用户角色列表
     * @param roles
     * @param userId
     * return
     * */
    private List<UserRole> getSysUserRoleList(List<Integer> roles, Integer userId){
        List<UserRole> list = new ArrayList<>();
        for (Integer integer : roles) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(integer);
            userRole.setUserId(userId);
            list.add(userRole);
        }
        return list;
    }

    @Transactional
    public void deleteById(final Integer userId){
        AdminUser sysAdminUser = adminUserMapper.selectByPrimaryKey(userId);

        adminUserMapper.deleteByPrimaryKey(userId);
        //清除用户角色关联关系
        userRoleService.deleteByUserId(userId);
        //删除用户redis中权限缓存
        stringRedisTemplate.delete(Constants.USER_PERMS + sysAdminUser.getMobile());
    }

    public void updataPwdById(AdminUser adminUser){
        adminUserMapper.updateByPrimaryKeySelective(adminUser);
    }

    /**
     * 查询用户手机号码是否存在
     * @param mobile
     * @param id
     * @return
     */
    public int getMobileIsExists(String mobile,Integer id){
        return adminUserMapper.selectMobileIsExists(mobile,id);
    }
}
