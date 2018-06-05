package com.allyes.minimec.api.web.controller;

import com.allyes.minimec.api.dto.CurrentUserInfo;
import com.allyes.minimec.api.service.LoginService;
import com.allyes.minimec.api.web.bind.annotation.CurrentUser;
import com.allyes.minimec.common.core.ResultDO;
import com.allyes.minimec.common.exception.BizException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author liuwei
 * @date 2018-03-10
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    LoginService loginService;


	@ApiOperation(value = "用户注册/登录：刘为", notes = "用户注册/登录接口")
    @RequestMapping(value = "v1/login", method = RequestMethod.POST)
    public ResultDO<String> login(
            @ApiParam(name = "mobile", value = "手机号码") @RequestParam String mobile,
            @ApiParam(name = "smsCode", value = "短信验证码") @RequestParam String smsCode,
            @ApiParam(name = "mobileImei", value = "终端编号") @RequestParam String mobileImei
            ) throws BizException {

        return loginService.login(mobile,smsCode);
    }

    @ApiOperation(value = "用户注销：刘为", notes = "用户注销接口")
    @ApiImplicitParams({@ApiImplicitParam(paramType="header",name="token",dataType="String",value="token",required = true,defaultValue="")})
    @RequestMapping(value = "/v1/authApi/logout", method = RequestMethod.POST)
    public ResultDO<?> logout(@CurrentUser CurrentUserInfo currentUserInfo) throws BizException {
        ResultDO<?> result = new ResultDO<>();
        try {
            loginService.logout(currentUserInfo.getMobile());
            result.setSuccess(true);
        } catch (Exception e) {
            throw new BizException("12");
        }
        return result;
    }

}
