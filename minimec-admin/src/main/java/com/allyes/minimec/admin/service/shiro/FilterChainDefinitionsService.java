package com.allyes.minimec.admin.service.shiro;

import java.util.Map;

/**
 * @author liuwei
 * @date 2018-03-10
 */
public interface FilterChainDefinitionsService {

    public static final String PREMISSION_STRING = "perms[{0}]"; // 资源结构格式

    /** 初始化系统权限资源配置 */
    public Map<String, String> initOtherPermission();

}
