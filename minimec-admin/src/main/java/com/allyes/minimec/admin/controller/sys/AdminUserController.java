package com.allyes.minimec.admin.controller.sys;

import com.allyes.minimec.admin.aop.bind.Function;
import com.allyes.minimec.admin.aop.bind.Operate;
import com.allyes.minimec.admin.security.realm.ShiroUser;
import com.allyes.minimec.admin.service.sys.AdminUserService;
import com.allyes.minimec.admin.service.sys.RoleService;
import com.allyes.minimec.common.core.ResultDO;
import com.allyes.minimec.common.core.SysCode;
import com.allyes.minimec.common.exception.BizException;
import com.allyes.minimec.domain.dto.sys.AdminUserDto;
import com.allyes.minimec.domain.dto.sys.ChangePasswordDto;
import com.allyes.minimec.domain.model.sys.AdminUser;
import com.allyes.minimec.domain.model.sys.Role;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author liuwei
 * @date 2018-03-10
 * 用户管理员控制器
 */
@Slf4j
@Controller
@RequestMapping("/sys/user")
@Function(value ="后台用户信息管理",moduleName = "系统管理",subModuleName = "")
public class AdminUserController
    {
    @Autowired
    AdminUserService adminUserService;

    @Autowired
    RoleService roleService;

    @RequestMapping(value = "/")
    public String index(Model model) throws BizException {
        List<Role> roleList = roleService.selectByAll();
        model.addAttribute("roleList",roleList);
        return "sys/user/list";
    }

    @ResponseBody
    @Operate(value = "后台用户信息查询")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public ResultDO<?> list(AdminUserDto adminUserDto) throws BizException {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        ResultDO<PageInfo<AdminUser>> result = new ResultDO<>();
        boolean isAdmin = shiroUser.getRoleSet().contains("1");
        result.setModule(adminUserService.findByPage(adminUserDto,isAdmin));
        result.setSuccess(true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/info/{id}",method = RequestMethod.GET)
    public ResultDO<?> info(@PathVariable("id") final int id) throws BizException {
        ResultDO<AdminUserDto> result = new ResultDO<>();
        result.setSuccess(true);
        result.setModule(adminUserService.selectById(id));
        return result;
    }

    @ResponseBody
    @Operate(value = "新增后台用户信息")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultDO<?> add(@RequestBody AdminUserDto adminUserDto) throws BizException {
        ResultDO<PageInfo<AdminUser>> result = new ResultDO<>();
        //获取用户手机号码是否存在
        int cnt = adminUserService.getMobileIsExists(adminUserDto.getMobile(),adminUserDto.getId());
        if(cnt == 0){
            adminUserService.addAdminUser(adminUserDto);
            result.setSuccess(true);
        }else{
            throw new BizException(SysCode.USER_MOBILE_EXISTS);
        }
        return result;
    }

    @ResponseBody
    @Operate(value = "修改后台用户信息")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResultDO<?> update(@RequestBody AdminUserDto adminUserDto) throws BizException {
        ResultDO<PageInfo<AdminUser>> result = new ResultDO<>();
        //获取用户手机号码是否存在
        int cnt = adminUserService.getMobileIsExists(adminUserDto.getMobile(),adminUserDto.getId());
        if(cnt == 0){
            adminUserService.updateAdminUser(adminUserDto);
            result.setSuccess(true);
        }else{
            throw new BizException(SysCode.USER_MOBILE_EXISTS);
        }
        return result;
    }

    @ResponseBody
    @Operate(value = "删除后台用户信息")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public ResultDO<?> delete(@PathVariable("id") final int id) throws BizException {
            ResultDO<AdminUser> result = new ResultDO<>();
        adminUserService.deleteById(id);
        result.setSuccess(true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getRoleList",method = RequestMethod.POST)
    public ResultDO<?> getRoleList() throws BizException {
        ResultDO<List<Role>> result = new ResultDO<>();
        List<Role> roleList = roleService.selectByAll();
        result.setModule(roleList);
        result.setSuccess(true);
        return result;
    }

    @RequestMapping(value = "/initPwd")
    public String changePwdIndex(Model model) throws BizException {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("mobile",shiroUser.getMobile());
        return "sys/user/password";
    }

    @ResponseBody
    @RequestMapping(value = "/checkPwd",method = RequestMethod.POST)
    public ResultDO<?> checkPwd(@RequestBody ChangePasswordDto changePasswordDto) throws BizException {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        ResultDO<?> result = new ResultDO<>();
        boolean res = false;
        if(BCrypt.checkpw(changePasswordDto.getPassword(),shiroUser.getPassword())){
            res = true;
        }
        result.setSuccess(res);
        return result;
    }

    @ResponseBody
    @Operate(value = "用户修改密码")
    @RequestMapping(value = "/changePwd",method = RequestMethod.POST)
    public ResultDO<?> changePwd(@RequestBody ChangePasswordDto changePasswordDto) throws BizException {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        ResultDO<?> result = new ResultDO<>();
        if(!BCrypt.checkpw(changePasswordDto.getPassword(),shiroUser.getPassword())){
            //验证旧密码是否正确
            throw new BizException(SysCode.CHANGEPWD_PWD_INCORRECT);
        }
        if(StringUtils.isEmpty(changePasswordDto.getNewpassword())){
            //验证新密码是否为空
            throw new BizException(SysCode.CHANGEPWD_PASSWORD_EMPTY);
        }
        if(changePasswordDto.getPassword().equals(changePasswordDto.getNewpassword())){
            //新密码不能与旧密码相同
            throw new BizException(SysCode.OLD_PASSWORD_NEQ);
        }
        if(!changePasswordDto.getNewpassword().equals(changePasswordDto.getNewpasswordagain())){
            //验证两次新密码是否一致
            throw new BizException(SysCode.CHANGEPWD_PASSWORD_NEQ);
        }
        AdminUser sysAdminUser = adminUserService.selectByMobile(shiroUser.getMobile().toString());
        AdminUser user = new AdminUser();
        user.setId(sysAdminUser.getId());
        String hashPwd = BCrypt.hashpw(changePasswordDto.getNewpassword(),sysAdminUser.getSalt());
        user.setPassword(hashPwd);

        adminUserService.updataPwdById(user);
        UsernamePasswordToken token = new UsernamePasswordToken(shiroUser.getMobile().toString(), changePasswordDto.getNewpassword());
        SecurityUtils.getSubject().login(token);
        result.setSuccess(true);
        return result;
    }
}
