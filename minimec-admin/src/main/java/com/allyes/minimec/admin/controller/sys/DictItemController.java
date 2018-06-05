package com.allyes.minimec.admin.controller.sys;

import com.allyes.minimec.admin.aop.bind.Function;
import com.allyes.minimec.admin.aop.bind.Operate;
import com.allyes.minimec.admin.security.realm.ShiroUser;
import com.allyes.minimec.common.constant.SysConst;
import com.allyes.minimec.common.core.ResultDO;
import com.allyes.minimec.common.exception.BizException;
import com.allyes.minimec.domain.dto.BasePageDto;
import com.allyes.minimec.domain.model.sys.DictItem;
import com.allyes.minimec.domain.service.DictItemService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author liuwei
 * @date 2018-03-10
 * 数据字典管理控制器说明
 */
@Slf4j
@Function(value ="数据字典管理",moduleName = "系统管理",subModuleName = "")
@Controller
@RequestMapping("/sys/dict")
public class DictItemController {

    @Autowired
    private DictItemService dictItemService;
   
    
    /**
     * 进入提示信息管理页面方法
     * @return
     * @throws BizException
     */
    @RequestMapping(value = "/")
    public String index(Model model) throws BizException {
        List<DictItem> list = dictItemService.selectByDictId(0);
        model.addAttribute("dictTypeList",list);
        return "sys/dict/list";
    }

    /**
     * 信息分页查询
     * @param basePageDto
     * @return
     * @throws BizException
     */

    @ResponseBody
    @Operate(value = "查询列表")
    @RequestMapping(value = "/list")
    public ResultDO<?> list(BasePageDto basePageDto) throws BizException {
        ResultDO<PageInfo<DictItem>> result = new ResultDO<>();
        result.setModule(dictItemService.findByPage(basePageDto));
        result.setSuccess(true);
        return result;
    }


    @ResponseBody
    @Operate(value = "添加数据字典")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultDO<?> add(@RequestBody DictItem dictItem) throws Exception {
        ResultDO<String> result = new ResultDO<>();
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        dictItem.setCreateBy(shiroUser.getId());
        dictItem.setCreateTime(new Date());
        dictItemService.insertSelective(dictItem);
        result.setSuccess(true);
        return result;
    }

    @Operate(value = "更新数据字典")
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultDO<?> update(@RequestBody DictItem dictItem) throws BizException {
        ResultDO<String> result = new ResultDO<>();
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        dictItem.setUpdateBy(shiroUser.getId());
        dictItem.setUpdateTime(new Date());
        if(dictItem.getStatus()==null){
            dictItem.setStatus(SysConst.USE_NO);
        }
        dictItemService.updateById(dictItem);
        result.setSuccess(true);
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    public ResultDO<DictItem> getOne(@PathVariable Integer id) throws BizException {
        ResultDO<DictItem> result = new ResultDO<>();
        DictItem dictItem = dictItemService.selectById(id);
        result.setSuccess(true);
        result.setModule(dictItem);
        return result;
    }
}
