package com.allyes.minimec.admin.controller.sys;

import com.allyes.minimec.admin.aop.bind.Function;
import com.allyes.minimec.admin.aop.bind.Operate;
import com.allyes.minimec.admin.security.realm.ShiroUser;
import com.allyes.minimec.admin.service.sys.RoleService;
import com.allyes.minimec.common.core.ResultDO;
import com.allyes.minimec.common.exception.BizException;
import com.allyes.minimec.domain.dto.BasePageDto;
import com.allyes.minimec.domain.dto.sys.RoleGrantFunDto;
import com.allyes.minimec.domain.model.sys.Role;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * @author liuwei
 * @date 2018-03-10
 */
@Slf4j
@Controller
@RequestMapping("/sys/role")
@Function(value ="角色信息管理",moduleName = "系统管理",subModuleName = "")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/")
    public String index() {
        return "sys/role/list";
    }


    @ResponseBody
    @Operate(value = "查询角色信息")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultDO<?> list(BasePageDto basePageDto) throws BizException {
        ResultDO<PageInfo<Role>> result = new ResultDO<>();
        result.setModule(roleService.findByPage(basePageDto));
        result.setSuccess(true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/info/{id}",method = RequestMethod.GET)
    public ResultDO<?> info(@PathVariable("id") final int id) throws BizException {
        ResultDO<Role> result = new ResultDO<>();
        result.setSuccess(true);
        result.setModule(roleService.selectById(id));
        return result;
    }

    @ResponseBody
    @Operate(value = "新增角色信息")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultDO<?> add(@RequestBody Role role) throws BizException {
        ResultDO<String> result = new ResultDO<>();
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        role.setCreateBy(shiroUser.getId());
        roleService.insertRole(role);
        result.setSuccess(true);
        return result;
    }

    @ResponseBody
    @Operate(value = "修改角色信息")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResultDO<?> update(@RequestBody Role role) throws BizException {
        ResultDO<String> result = new ResultDO<>();
        roleService.updateRole(role);
        return result;
    }

    @ResponseBody
    @Operate(value = "删除角色信息")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public ResultDO<?> delete(@PathVariable("id") final int id) throws BizException {
        ResultDO<String> result = new ResultDO<>();
        roleService.deleteById(id);
        result.setSuccess(true);
        return result;
    }

    @ResponseBody
    @Operate(value = "角色角色赋权")
    @RequestMapping(value = "/grantFun",method = RequestMethod.POST)
    public ResultDO<?> grantFun(@RequestBody RoleGrantFunDto roleGrantFunDto) throws BizException {
        ResultDO<String> result = new ResultDO<>();
        roleService.grantFun(roleGrantFunDto);
        result.setSuccess(true);
        return result;
    }


}
