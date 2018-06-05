package com.allyes.minimec.admin.service.shiro;

import com.allyes.minimec.admin.service.sys.ResourceService;
import com.allyes.minimec.domain.model.sys.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuwei
 * @date 2018-03-10
 */
@Slf4j
@Service
public class ShiroFilterChainDefinitionsService extends AbstractFilterChainDefinitionsService {


    @Autowired
    private ResourceService resourceService;


    @Override
    public Map<String, String> initOtherPermission() {
        final LinkedHashMap<String, String> filterChains = (LinkedHashMap<String, String>)obtainPermission();

        List<Resource> _resources = resourceService.selectAllByUrlNotNull();
        try {
            for (Resource resource : _resources) {
                if (StringUtils.isNotEmpty(resource.getPermission())) {
                    filterChains.put(resource.getUrl(), MessageFormat.format(PREMISSION_STRING, new Object[]{resource.getPermission()}));
                }
            }
            filterChains.put("/sendBackPwdMsg", "anon");
            filterChains.put("/backPassword", "anon");
            filterChains.put("/**", "authc");
            log.debug("【动态链】：" + filterChains.toString());
        } catch (Exception e) {
            log.error("资源属性不完整");
        }
        log.debug("set definitions success... ");
        return filterChains;
    }

}
