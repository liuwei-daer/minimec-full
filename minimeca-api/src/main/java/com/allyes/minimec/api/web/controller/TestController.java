package com.allyes.minimec.api.web.controller;

import com.allyes.minimec.api.dto.CurrentUserInfo;
import com.allyes.minimec.api.rabbitmq.Sender;
import com.allyes.minimec.api.web.bind.annotation.CurrentUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuwei
 * @date 2018-03-10
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private Sender sender;

    /**
     * 不需要登录
     */
    @ApiOperation(value = "测试不需要token", notes = "测试不需要token接口")
    @GetMapping("/hello")
    public String helloUser(){
        return "hello User!";
    }

    /**
     * 需要登录
     * @return
     */
    @ApiOperation(value = "测试需要token", notes = "测试需要token接口")
    @GetMapping("/authApi/admin")
    @ApiImplicitParams({@ApiImplicitParam(paramType="header",name="token",dataType="String",value="token",required = true,defaultValue="")})
    public CurrentUserInfo helloAdmin(@CurrentUser CurrentUserInfo currentUserInfo) throws Exception{
        return currentUserInfo;
    }


    /**
     * 不需要登录
     */
    @ApiOperation(value = "测试不需要token", notes = "测试不需要token接口")
    @GetMapping("/mqtt")
    public String sendMqtt(String msg){
        sender.send(msg);
        return "hello User!";
    }

}
