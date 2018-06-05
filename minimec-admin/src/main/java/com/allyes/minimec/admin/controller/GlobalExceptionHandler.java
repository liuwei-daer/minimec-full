package com.allyes.minimec.admin.controller;

import com.allyes.minimec.common.core.ResultDO;
import com.allyes.minimec.common.exception.BizException;
import com.allyes.minimec.domain.service.TipInfoCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

/**
 * @author liuwei
 * @date 2018-03-10
 * 全局异常消息处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {


	@Autowired
	TipInfoCacheService tipInfoCacheService;

	@ResponseBody
    @ExceptionHandler(value = BizException.class)
    public ResultDO<?> jsonErrorHandler(HttpServletRequest req, BizException e) throws Exception {
    	ResultDO<String> rs = new ResultDO<>();
    	String resultCode=e.getErrorCode();
		String errorMessage=e.getErrorMsg();
		//非动态消息
		if(!e.isDynamic()){
			errorMessage= tipInfoCacheService.getTipMsg(resultCode);

			if(null != e.getErrors() && e.getErrors().length > 0){
				MessageFormat messageFormat = new MessageFormat(errorMessage);
				errorMessage = messageFormat.format(e.getErrors());
			}
		}

    	rs.setErrorMessage(errorMessage);
    	rs.setResultCode(resultCode);
    	rs.setModule(null);
    	rs.setSuccess(false);
        return rs;
    }

}
