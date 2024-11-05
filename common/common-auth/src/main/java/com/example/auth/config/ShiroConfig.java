package com.example.auth.config;

import com.example.auth.jwt.JwtCredentialsMatcher;
import com.example.auth.jwt.JwtFilter;
import com.example.auth.jwt.JwtRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 不需要鉴权
     */
    private static final String JWT_ANNO_NAME = "anon";

    /**
     * JWT过滤器名称
     */
    private static final String JWT_FILTER_NAME = "jwtFilter";

    /**
     * Shiro过滤器名称
     */
    private static final String SHIRO_FILTER_NAME = "shiroFilter";

    /**
     * 安全管理器名称
     */
    private static final String SECURITY_MANAGER = "securityManager";

    @Bean
    public CredentialsMatcher credentialsMatcher() {
        return new JwtCredentialsMatcher();
    }

    @Bean
    public JwtRealm jwtRealm() {
        JwtRealm jwtRealm = new JwtRealm();
        jwtRealm.setCachingEnabled(false);
        jwtRealm.setCredentialsMatcher(credentialsMatcher());
        return jwtRealm;
    }

    @Bean
    public SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        // 关闭shiro自带的session
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    @Bean
    public DefaultSubjectDAO subjectDAO() {
        DefaultSubjectDAO defaultSubjectDAO = new DefaultSubjectDAO();
        defaultSubjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator());
        return defaultSubjectDAO;
    }

    @Bean(SECURITY_MANAGER)
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(jwtRealm());
        securityManager.setSubjectDAO(subjectDAO());
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    @Bean(SHIRO_FILTER_NAME)
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, ShiroFilterProperties shiroFilterProperties) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);

        // 添加自己的过滤器
        Map<String, Filter> filterMap = new LinkedHashMap<String, Filter>(1) {{
            put(JWT_FILTER_NAME, new JwtFilter());
        }};
        factoryBean.setFilters(filterMap);

        /*
         * 自定义url规则
         * http://shiro.apache.org/web.html#urls-
         */
        Map<String, String> filterRuleMap = new LinkedHashMap<>(32);
        shiroFilterProperties.getAnno().forEach(item -> filterRuleMap.put(item, JWT_ANNO_NAME));
        shiroFilterProperties.getFilter().forEach(item -> filterRuleMap.put(item, JWT_FILTER_NAME));
        // 所有请求通过我们自己的JWT Filter
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}