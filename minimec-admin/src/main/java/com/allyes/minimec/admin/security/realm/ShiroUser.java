package com.allyes.minimec.admin.security.realm;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author liuwei
 * @date 2018-03-10
 */
public @Data class ShiroUser implements Serializable {

    private Integer id;
    private String userName;
    private String realName;
    private String mobile;
    private String password;
    private String avatarUrl;
    private Set<String> roleSet;
    private Set<String> permissionSet;

}
