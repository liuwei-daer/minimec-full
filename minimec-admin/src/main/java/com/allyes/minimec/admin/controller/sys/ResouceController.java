package com.allyes.minimec.admin.controller.sys;

import com.allyes.minimec.admin.aop.bind.Function;
import com.allyes.minimec.admin.aop.bind.Operate;
import com.allyes.minimec.admin.security.realm.ShiroUser;
import com.allyes.minimec.admin.service.sys.ResourceService;
import com.allyes.minimec.common.core.ResultDO;
import com.allyes.minimec.common.exception.BizException;
import com.allyes.minimec.domain.dto.sys.ResourceTreeDto;
import com.allyes.minimec.domain.model.sys.Resource;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liuwei
 * @date 2018-03-10
 */
@Slf4j
@Controller
@RequestMapping("/sys/resource")
@Function(value ="资源信息管理",moduleName = "系统管理",subModuleName = "")
public class ResouceController {

    @Autowired
    private ResourceService resourceService;

    @RequestMapping(value = "/")
    public String index(Model model) {
        List<Resource> list = resourceService.selectResource();
        model.addAttribute("resource",list);
        return "sys/resource/list";
    }

    @ResponseBody
    @Operate(value = "查询资源信息")
    @RequestMapping(value = "/findResourceTable", method = RequestMethod.POST)
    public ResultDO<?> findResourceTable() {
        ResultDO result = new ResultDO<>();
        try {
            List<Resource> list = resourceService.selectResource();
            result.setModule(list);
            return result;
        } catch (Exception e) {
            log.error("查询失败",e);
        }

        return result;
    }

    @RequestMapping(value = "/findById/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResultDO<?> findById(@PathVariable("id") final int id) {
        ResultDO result = new ResultDO<>();
        try {
            Resource resource = resourceService.findResourceById(id);
            result.setModule(resource);
            result.setSuccess(true);
            return result;
        } catch (Exception e) {
            log.error("查询失败",e);
        }
        return result;
    }

    @ResponseBody
    @Operate(value = "新增资源信息")
    @RequestMapping(value = "/add")
    public ResultDO<?> add(@RequestBody Resource resource) throws BizException {
        ResultDO<PageInfo<Resource>> result = new ResultDO<>();
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        resource.setCreateBy(shiroUser.getId());
        resourceService.insertResouce(resource);
        result.setSuccess(true);
        return result;
    }

    @ResponseBody
    @Operate(value = "修改资源信息")
    @RequestMapping(value = "/update")
    public ResultDO<?> update(@RequestBody Resource resource) throws BizException {
        ResultDO<PageInfo<Resource>> result = new ResultDO<>();
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        resource.setCreateBy(shiroUser.getId());
        resourceService.updateResouce(resource);
        result.setSuccess(true);
        return result;
    }

    @ResponseBody
    @Operate(value = "删除资源信息")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResultDO<?> delete(@PathVariable("id") final int id) throws BizException{
        ResultDO result = new ResultDO<>();
        resourceService.delResource(id);
        result.setSuccess(true);
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/resourceTree/{roleId}", method = RequestMethod.GET)
    public ResultDO<?> resourceTree(@PathVariable("roleId") final int roleId) {
        ResultDO result = new ResultDO<>();
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        List<ResourceTreeDto> list = resourceService.getTreeData(roleId,shiroUser.getUserName(),shiroUser.getId());
        result.setModule(list);
        result.setSuccess(true);
        return result;
    }

}
