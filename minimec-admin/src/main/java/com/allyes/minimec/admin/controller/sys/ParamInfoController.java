package com.allyes.minimec.admin.controller.sys;

import com.allyes.minimec.admin.aop.bind.Function;
import com.allyes.minimec.admin.aop.bind.Operate;
import com.allyes.minimec.admin.security.realm.ShiroUser;
import com.allyes.minimec.common.core.ResultDO;
import com.allyes.minimec.common.exception.BizException;
import com.allyes.minimec.domain.dto.BasePageDto;
import com.allyes.minimec.domain.model.sys.ParamInfo;
import com.allyes.minimec.domain.service.ParamInfoCacheService;
import com.allyes.minimec.domain.service.ParamInfoService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author liuwei
 * @date 2018-03-10
 * 系统参数控制器
 */
@Slf4j
@Controller
@RequestMapping("/sys/param")
@Function(value ="系统参数管理",moduleName = "系统管理",subModuleName = "")
public class ParamInfoController {


    @Autowired
    private ParamInfoService paramInfoService;

    @Autowired
    private ParamInfoCacheService paramInfoCacheService;


    /**
     * 进入管理页面
     * @return
     * @throws BizException
     */
    @RequestMapping(value = "/")
    public String index() throws BizException {
        return "sys/param/list";
    }

    /**
     * 提示信息分页查询
     * @param basePageDto
     * @return
     * @throws BizException
     */
    @Operate(value = "查询系统参数列表")
    @ResponseBody
    @RequestMapping(value = "/list")
    public ResultDO<?> list(BasePageDto basePageDto) throws BizException {
        ResultDO<PageInfo<ParamInfo>> result = new ResultDO<>();
        result.setModule(paramInfoService.findByPage(basePageDto));
        result.setSuccess(true);
        return result;
    }

    
    /**
     * 同步缓存信息
     * @return
     * @throws BizException
     */
    @ResponseBody
    @Operate(value = "同步缓存")
    @RequestMapping(value = "/syncache")
    public ResultDO<?> syncache() throws BizException {
        ResultDO<?> result = new ResultDO<>();
        result.setSuccess(true);
        paramInfoCacheService.reload();
        return result;
    }


    @ResponseBody
    @Operate(value = "添加参数")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultDO<?> add(ParamInfo paramInfo) throws Exception {
        ResultDO<String> result = new ResultDO<>();
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        paramInfo.setCreateBy(shiroUser.getId());
        paramInfo.setCreateTime(new Date());
        int id = paramInfoService.insert(paramInfo);
        if (id>0){
            paramInfoCacheService.putIt(paramInfo.getParamKey().trim(), paramInfo.getParamValue());
        }
        return result;
    }
    
    /**
     * 更新及缓存
     * @param paramInfo
     * @return
     * @throws BizException
     */
    @ResponseBody
    @Operate(value = "更新参数")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultDO<?> update(ParamInfo paramInfo) throws BizException {
        ResultDO<String> result = new ResultDO<>();
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        paramInfo.setUpdateBy(shiroUser.getId());
        paramInfo.setUpdateTime(new Date());
        
        boolean isSuccess=paramInfoService.updateById(paramInfo);
        
        if(isSuccess){
            paramInfoCacheService.putIt(paramInfo.getParamKey().trim(), paramInfo.getParamValue());
        }
        result.setSuccess(true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    public ResultDO<ParamInfo> getOne(@PathVariable Integer id) throws BizException {
        ResultDO<ParamInfo> result = new ResultDO<>();
        ParamInfo info=paramInfoService.selectById(id);
        result.setSuccess(true);
        result.setModule(info);
        
        return result;
    }

}
