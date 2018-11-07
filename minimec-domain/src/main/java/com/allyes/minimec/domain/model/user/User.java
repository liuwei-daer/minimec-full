package com.allyes.minimec.domain.model.user;

import lombok.Data;

@Data
public class User {
    private Integer id;

    /**用户名称*/
    private String nickName;

    /**客户真实姓名*/
    private String realName;

    /**用户手机号码*/
    private String mobile;

    /**用户头像地址*/
    private String avatarUrl;


}