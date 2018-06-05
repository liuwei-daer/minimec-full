package com.allyes.minimec.admin.controller.sys;

import com.allyes.minimec.admin.aop.bind.Function;
import com.allyes.minimec.admin.aop.bind.Operate;
import com.allyes.minimec.admin.service.sys.OperatLogService;
import com.allyes.minimec.common.core.ResultDO;
import com.allyes.minimec.common.exception.BizException;
import com.allyes.minimec.domain.dto.sys.OperatLogDto;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author liuwei
 * @date 2018-03-10
 * 系统日志管理控制器
 */

@Slf4j
@Function(value ="系统日志管理",moduleName = "系统管理",subModuleName = "")
@Controller
@RequestMapping("/sys/operatLog")
public class OperatLogController {

    @Autowired
    OperatLogService operatLogService;

    @RequestMapping(value = "/")
    public String index(Model model) throws BizException {
        return "sys/operatLog/list";
    }

    @ResponseBody
    @Operate(value="系统日志列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultDO<?> list(OperatLogDto operatLogDto) throws BizException {
        ResultDO<PageInfo<OperatLogDto>> result = new ResultDO<>();
        result.setModule(operatLogService.findByPage(operatLogDto));
        result.setSuccess(true);
        return result;
    }
}
