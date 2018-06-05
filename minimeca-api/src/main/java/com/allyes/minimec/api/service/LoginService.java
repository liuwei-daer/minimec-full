package com.allyes.minimec.api.service;

import com.allyes.minimec.api.dto.CurrentUserInfo;
import com.allyes.minimec.common.constant.Constants;
import com.allyes.minimec.common.core.ResultDO;
import com.allyes.minimec.common.core.SysCode;
import com.allyes.minimec.common.exception.BizException;
import com.allyes.minimec.domain.model.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author liuwei
 * @date 2018-03-10
 */
@Slf4j
@Service
public class LoginService {

    private @Value("${jwt.api.token.app-secret}") String appSecret;

    private @Value("${jwt.api.token.app-issuer}") String appIssuer;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    AuthTokenService authTokenService;


    @Transactional
    public ResultDO<String> login(String mobile, String smsCode) throws BizException {
        ResultDO<String> rs=new ResultDO<String>();
        if(StringUtils.isEmpty(mobile)){
            throw new BizException(SysCode.BACKPWD_MOBILE_EMPTY);
        }
        if (StringUtils.isEmpty(smsCode)) {
            throw new BizException(SysCode.COMMON_VERIFY_CODE_ERROR);
        }

        //验证用户是否存在
        User user = new User();
        user.setMobile("15618956058");
        user.setNickName("daer");
        user.setRealName("刘为");
        String secret = DigestUtils.md5Hex(RandomStringUtils.randomAlphanumeric(10));

        //将token加入到redis中去
        CurrentUserInfo currentUserInfo = new CurrentUserInfo();
        currentUserInfo.setMobile(mobile);
        currentUserInfo.setAvatarUrl(user.getAvatarUrl());
        currentUserInfo.setNickName(user.getNickName());
        currentUserInfo.setRealName(user.getRealName());
        currentUserInfo.setUserId(user.getId());
        currentUserInfo.setSecret(secret);

        try {
            ObjectMapper mapper = new ObjectMapper();
            ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
            valueOperations.set(Constants.APP_TOKEN+mobile, mapper.writeValueAsString(currentUserInfo));
            rs.setModule(authTokenService.getToken(mobile.toString(),secret));
            rs.setSuccess(true);
            rs.setResultCode(SysCode.TRUE);
        } catch (Exception e) {
            log.error("登录出错",e);
            throw new BizException(SysCode.COMMON_SERVICE_ERROR);
        }
        return rs;
    }

    /**
     * app手机退出登陆接口
     * @param mobile 手机号码
     * @return
     * */
    public void logout(final String mobile){
        stringRedisTemplate.delete(Constants.APP_TOKEN+mobile);
    }

}
