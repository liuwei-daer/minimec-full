package com.allyes.minimec.admin.service.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author liuwei
 * @date 2018-03-10
 */
@Slf4j
public abstract class AbstractFilterChainDefinitionsService implements FilterChainDefinitionsService {

    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @PostConstruct
    public void intiPermission() {

        synchronized (shiroFilterFactoryBean) {

            AbstractShiroFilter shiroFilter = null;

            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            } catch (Exception e) {
                log.error(e.getMessage());
            }

            // 获取过滤管理器
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                    .getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

            // 清空初始权限配置
            manager.getFilterChains().clear();
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();

            // 重新构建生成
            shiroFilterFactoryBean.setFilterChainDefinitionMap(initOtherPermission());
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();

            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                manager.createChain(url, chainDefinition);
            }
            log.debug("init resource permission success...");
        }
    }

    /**
     * 读取初始化资源文件
     */
    protected Map<String, String> obtainPermission() {
        final LinkedHashMap<String, String> filterChains = new LinkedHashMap<>();
        filterChains.put("/css/**", "anon");
        filterChains.put("/js/**", "anon");
        filterChains.put("/img/**", "anon");
        filterChains.put("/fonts/**", "anon");
        filterChains.put("/plugins/**", "anon");
        filterChains.put("/druid/**", "user");
        filterChains.put("/logout", "logout");
        filterChains.put("/loginPost", "anon");
        return filterChains;
    }

}
