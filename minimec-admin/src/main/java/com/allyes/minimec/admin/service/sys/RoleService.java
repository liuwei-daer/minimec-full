package com.allyes.minimec.admin.service.sys;

import com.allyes.minimec.domain.dto.BasePageDto;
import com.allyes.minimec.domain.dto.sys.RoleGrantFunDto;
import com.allyes.minimec.domain.mapper.sys.RoleMapper;
import com.allyes.minimec.domain.mapper.sys.RoleResourceMapper;
import com.allyes.minimec.domain.mapper.sys.UserRoleMapper;
import com.allyes.minimec.domain.model.sys.Role;
import com.allyes.minimec.domain.model.sys.RoleResource;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author liuwei
 * @date 2018-03-10
 */
@Slf4j
@Service
public class RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    RoleResourceMapper roleResourceMapper;

    @Autowired
    ResourceService resourceService;

    public List<Role> selectByUserId(final Integer userId){
        return roleMapper.selectByUserId(userId);
    }

    public List<Role> selectByAll(){
        return roleMapper.selectByExample(null);
    }


    public PageInfo<Role> findByPage(BasePageDto basePageDto){
        //分页
        if (!StringUtils.isEmpty(basePageDto.getPage()) && !StringUtils.isEmpty(basePageDto.getRows())) {
            PageHelper.startPage(basePageDto.getPage(), basePageDto.getRows());
        }
        List<Role> list = roleMapper.selectBySearch(basePageDto.getSearchVal());
        PageInfo<Role> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


    /**
     * 新增角色并保存角色对应的组织
     * @param role
     *
     * */
    public void insertRole(Role role){
        roleMapper.insertSelective(role);
    }

    /**
     * 根据角色ID查询角色信息
     * @param roleId
     * return
     *
     * */
    public Role selectById(final Integer roleId){
        return roleMapper.selectByPrimaryKey(roleId);
    }

    /**
     * 修改角色信息
     * @param role
     * */
    public void updateRole(Role role){
        //修改角色角色
        roleMapper.updateByPrimaryKeySelective(role);

    }

    /**
     * 删除角色
     *
     * */
    @Transactional
    public void deleteById(final Integer roleId){
        //删除角色信息
        roleMapper.deleteByPrimaryKey(roleId);
        //删除用户角色关联信息
        userRoleMapper.deleteByRoleId(roleId);
        //清除原有的角色关联资源信息
        roleResourceMapper.delByRoleId(roleId);
    }

    /**
     * 角色授权
     * @param roleGrantFunDto
     *
     * */
    @Transactional
    public void grantFun(RoleGrantFunDto roleGrantFunDto){
        //获取该资源的所有父级节点
        Set<Integer> resourceSet = new HashSet<>();
        for (Integer resourceId : roleGrantFunDto.getResourceList()) {
            String pids = resourceService.selectParentIdsById(resourceId);
            if (!StringUtils.isEmpty(pids)){
                String[] pid = pids.split(",");
                for (String s : pid) {
                    resourceSet.add(Integer.parseInt(s));
                }
            }
        }
        resourceSet.addAll(roleGrantFunDto.getResourceList());

        //清除原有的角色关联资源信息
        roleResourceMapper.delByRoleId(roleGrantFunDto.getRoleId());

        //重新关联角色资源信息
        List<RoleResource> list = new ArrayList<>();
        for (Integer resourceId : resourceSet) {
            RoleResource roleResource = new RoleResource();
            roleResource.setRoleId(roleGrantFunDto.getRoleId());
            roleResource.setResourceId(resourceId);
            list.add(roleResource);
        }
        if(list.size() > 0) {
            roleResourceMapper.insertBatch(list);
        }
    }

    /**
     * 验证角色是否已经存在
     * @param role
     * @return
     */
    public int isExistRole(Role role){
        return roleMapper.selectIsExistRole(role.getId(),role.getRoleName());
    }

}
