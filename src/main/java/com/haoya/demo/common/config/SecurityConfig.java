package com.haoya.demo.common.config;

import com.haoya.demo.app.sys.entity.SysDept;
import com.haoya.demo.app.sys.entity.SysRole;
import com.haoya.demo.app.sys.entity.SysUser;
import com.haoya.demo.app.sys.entity.SysUserDetails;
import com.haoya.demo.app.sys.repository.SysDeptRepository;
import com.haoya.demo.app.sys.repository.SysRoleRepository;
import com.haoya.demo.app.sys.repository.SysUserRepository;
import com.haoya.demo.app.sys.repository.SysUserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
        .and()
                .formLogin()
                .loginPage("/sys/login")
                .loginProcessingUrl("/auth/form")
                .failureUrl("/sys/login?error=true")
                .defaultSuccessUrl("/index")
                .permitAll()
        .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(hyLogoutSuccessHandler)
                .permitAll()
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
                if(isAjaxRequest(request)){
                    response.setHeader("X-Redirect", "/sys/login");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED,e.getMessage());
                }else{
                    response.sendRedirect("/sys/login");
                }
            }
            public boolean isAjaxRequest(HttpServletRequest request) {
                String ajaxFlag = request.getHeader("X-Requested-With");
                return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
            }
        });
        //.and()
        //    .requiresChannel()
        //    .antMatchers("/**").requiresSecure()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                //用户基本信息
                SysUser user = sysUserRepository.findByUsername(username);
                if(null == user)
                    return null;

                //角色信息
                List<SysRole> roles = sysRoleRepository.findByUserId(user.getUserId());

                //部门信息
                SysDept dept = sysDeptRepository.findByUserId(user.getUserId());

                UserDetails userDetails = new SysUserDetails(user, roles, dept);
                return userDetails;
            }
        })
        .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity security) throws Exception {
        security.ignoring().antMatchers("/thirdparty/**", "/common/**");
        //security.ignoring().antMatchers("/**");
    }

    @Autowired
    SysUserRepository sysUserRepository;

    @Autowired
    SysRoleRepository sysRoleRepository;

    @Autowired
    SysDeptRepository sysDeptRepository;

    @Autowired
    private HyLogoutSuccessHandler hyLogoutSuccessHandler;
}
