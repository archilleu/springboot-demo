package com.haoya.demo.common.config;

import com.haoya.demo.app.sys.entity.SysDept;
import com.haoya.demo.app.sys.entity.SysRole;
import com.haoya.demo.app.sys.entity.SysUser;
import com.haoya.demo.app.sys.entity.SysUserDetails;
import com.haoya.demo.app.sys.repository.SysDeptRepository;
import com.haoya.demo.app.sys.repository.SysRoleRepository;
import com.haoya.demo.app.sys.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/*/admin/**")
                .hasRole("admin")
                .and()
                .authorizeRequests()
                .antMatchers("/api/*/guest/**")
                .access("hasRole('guest') or hasRole('user')")
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/auth/form")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/main")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(hyLogoutSuccessHandler)
                .permitAll()
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                /**
                 * 归根结底都是缓存记录。用session和用redis方案的区别有两个：
                 * 1.一个用web服务器的缓存，一个用单独的服务器缓存，涉及到两种不同的运维方式。
                 * 2.用session直接支持对象存储，用redis需要再做一层对象与redis的数据类型的转换。
                 */
                .tokenValiditySeconds(60*60*24) //remember me 过期时间
                .and()
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
                    //ajax请求返回401，web返回301
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                        if (isAjaxRequest(request)) {
                            response.setHeader("X-Redirect", "/login");
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                        } else {
                            response.sendRedirect("/login");
                        }
                    }

                    public boolean isAjaxRequest(HttpServletRequest request) {
                        String ajaxFlag = request.getHeader("X-Requested-With");
                        return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
                    }
                });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                //用户基本信息
                SysUser user = sysUserRepository.findByUsername(username);
                if (null == user)
                    return null;

                //角色信息
                List<SysRole> roles = sysRoleRepository.findByUserId(user.getUserId());

                //部门信息
                SysDept dept = sysDeptRepository.findByUserId(user.getUserId());

                //权限信息
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                for(SysRole rs : roles) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + rs.getRoleName()));
                }

                UserDetails userDetails = new SysUserDetails(user, roles, dept, authorities);
                return userDetails;
            }
        })
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity security) throws Exception {
        security.ignoring().antMatchers("/favicon.ico", "/thirdparty/**", "/common/**");
        //security.ignoring().antMatchers("/**");
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl persistentTokenRepository = new JdbcTokenRepositoryImpl();
        persistentTokenRepository.setDataSource(dataSource);
        return persistentTokenRepository;
    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    SysUserRepository sysUserRepository;

    @Autowired
    SysRoleRepository sysRoleRepository;

    @Autowired
    SysDeptRepository sysDeptRepository;

    @Autowired
    private HyLogoutSuccessHandler hyLogoutSuccessHandler;
}
