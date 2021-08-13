package com.starter.base.config;

import com.starter.base.shiro.jwt.JWTFilter;
import com.starter.base.shiro.jwt.JwtCredentialsMatcher;
import com.starter.base.shiro.jwt.JwtRealm;
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
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * JWT过滤器名称
     */
    private static final String JWT_FILTER_NAME = "jwtFilter";

    /**
     * Shiro过滤器名称
     */
    private static final String SHIRO_FILTER_NAME = "shiroFilter";

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

    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(jwtRealm());
        securityManager.setSubjectDAO(subjectDAO());
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    @Bean(SHIRO_FILTER_NAME)
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);

        // 添加自己的过滤器
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put(JWT_FILTER_NAME, new JWTFilter());
        factoryBean.setFilters(filterMap);

        /*
         * 自定义url规则
         * http://shiro.apache.org/web.html#urls-
         */
        Map<String, String> filterRuleMap = new HashMap<>();
        filterRuleMap.put("/user/login", "anon");
        // swagger
        filterRuleMap.put("/swagger-ui.html", "anon");
        filterRuleMap.put("/swagger-resources/**", "anon");
        filterRuleMap.put("/v2/api-docs", "anon");
        filterRuleMap.put("/webjars/springfox-swagger-ui/**", "anon");
        // 所有请求通过我们自己的JWT Filter
        filterRuleMap.put("/**", JWT_FILTER_NAME);
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