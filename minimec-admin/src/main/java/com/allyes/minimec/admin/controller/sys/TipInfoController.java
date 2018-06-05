package com.allyes.minimec.admin.controller.sys;

import com.allyes.minimec.admin.aop.bind.Function;
import com.allyes.minimec.admin.aop.bind.Operate;
import com.allyes.minimec.admin.security.realm.ShiroUser;
import com.allyes.minimec.common.core.ResultDO;
import com.allyes.minimec.common.exception.BizException;
import com.allyes.minimec.domain.dto.BasePageDto;
import com.allyes.minimec.domain.model.sys.TipInfo;
import com.allyes.minimec.domain.service.TipInfoCacheService;
import com.allyes.minimec.domain.service.TipInfoService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author liuwei
 * @date 2017-03-10
 * 提示消息管理控制器
 */
@Slf4j
@Controller
@RequestMapping("/sys/tip")
@Function(value ="提示信息管理",moduleName = "系统管理",subModuleName = "")
public class TipInfoController {

    @Autowired
    private TipInfoCacheService tipInfoCacheService;

    @Autowired
    private TipInfoService tipInfoService;

    /**
     * 进入提示信息管理页面
     * @return
     * @throws BizException
     */
    @RequestMapping(value = "/")
    public String index() throws BizException {
        return "sys/tip/list";
    }

    /**
     * 提示信息分页查询
     * @param basePageDto
     * @return
     * @throws BizException
     */
    @Operate(value = "提示信息查询")
    @ResponseBody
    @RequestMapping(value = "/list")
    public ResultDO<?> list(BasePageDto basePageDto) throws BizException {
        ResultDO<PageInfo<TipInfo>> result = new ResultDO<>();
        result.setModule(tipInfoService.findByPage(basePageDto.getPage(),basePageDto.getRows(),basePageDto.getSearchVal()));
        result.setSuccess(true);
        return result;
    }

    
    /**
     * 同步整个提示的缓存信息
     * @return
     * @throws BizException
     */
    @Operate(value = "同步整个提示的缓存信息")
    @ResponseBody
    @RequestMapping(value = "/syncache")
    public ResultDO<?> syncache() throws BizException {
        ResultDO<?> result = new ResultDO<>();
        result.setSuccess(true);
        tipInfoCacheService.reload();
        return result;
    }
    
    /**
     * 添加
     * @param tipInfo
     * @return
     * @throws BizException
     */
    @Operate(value = "添加提示信息")
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultDO<?> add(@ModelAttribute TipInfo tipInfo) throws BizException {
        ResultDO<String> result = new ResultDO<>();
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        tipInfo.setCreateBy(shiroUser.getId());
        tipInfo.setCreateTime(new Date());

        try {
            Integer tipId=this.tipInfoService.insertIt(tipInfo);
            if(tipId!=null && tipId>0){
                tipInfoCacheService.putIt(tipInfo.getTipCode().trim(), tipInfo.getTipMsg().trim());
            }
        } catch (Exception ex) {
            throw new BizException();
        }
        result.setSuccess(true);
        return result;
    }
    
    /**
     * 更新消息提示及缓存
     * @param tipInfo
     * @return
     * @throws BizException
     */
    @Operate(value = "更新提示信息")
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultDO<?> update(@ModelAttribute TipInfo tipInfo) throws BizException {

        ResultDO<String> result = new ResultDO<>();
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        tipInfo.setUpdateBy(shiroUser.getId());
        tipInfo.setUpdateTime(new Date());
        
        boolean isSuccess=tipInfoService.updateById(tipInfo);
        
        if(isSuccess){
            tipInfoCacheService.putIt(tipInfo.getTipCode().trim(), tipInfo.getTipMsg().trim());
        }
        result.setSuccess(true);
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    public ResultDO<TipInfo> getOne(@PathVariable Integer id) throws BizException {
        ResultDO<TipInfo> result = new ResultDO<>();
        TipInfo info=this.tipInfoService.selectById(id);
        result.setSuccess(true);
        result.setModule(info);
        return result;
    }
    
}
