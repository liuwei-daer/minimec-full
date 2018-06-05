package com.allyes.minimec.api.interceptor;

import com.allyes.minimec.api.dto.CurrentUserInfo;
import com.allyes.minimec.api.service.AuthTokenService;
import com.allyes.minimec.common.constant.Constants;
import com.allyes.minimec.common.core.ResultDO;
import com.allyes.minimec.common.exception.UnknownLoginException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liuwei
 * @date 2018-03-12
 */
@Slf4j
public class AuthHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    AuthTokenService authTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if (method.equals("OPTIONS")){
            return true;
        }
        String token = request.getHeader("token");

        if(StringUtils.isBlank(token)){
            onLoginFail(response,"无效凭证，请重新登录！");
            return false;
        }
        log.debug("auth is :"+token);

        if(!authTokenService.verifyToken(token)){
            onLoginFail(response,"无效凭证，请重新登录！");
            return false;
        }
        try {
            CurrentUserInfo currentUserInfo = authTokenService.authLogin(token, Constants.APP_TOKEN);
            request.setAttribute(Constants.APP_USER,currentUserInfo);
        } catch (UnknownLoginException ule){
            onLoginFail(response,ule.getMessage());
            return false;
        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    private void onLoginFail(HttpServletResponse response, String msg) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ResultDO<?> result = new ResultDO<>();
        result.setSuccess(false);
        result.setResultCode("-99");
        result.setErrorMessage(msg);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(result));
    }


    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}