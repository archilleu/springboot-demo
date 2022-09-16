package com.example.auth.jwt;

import com.example.auth.util.GetTokenUtil;
import com.example.auth.util.HttpServletResponseUtil;
import com.example.auth.util.JwtUtil;
import com.example.common.base.exception.ServerError;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 任何在ShiroConfig配置不是anon的路径都要经过本过滤器
 * 成功调用：isAccessAllowed->createToken->onLoginSuccess
 * 失败调用：isAccessAllowed->onAccessDenied
 *
 * @author cjy
 */

@Slf4j
public class JwtFilter extends AuthenticatingFilter {

    /**
     * 将JWT Token包装成AuthenticationToken
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        String token = GetTokenUtil.getToken((HttpServletRequest) servletRequest);
        if (JwtUtil.isExpired(token)) {
            // 将继续调用onAccessDenied方法
            throw new ShiroException("JWT Token expired");
        }

        //TODO:如果开启redis二次校验，或者设置为单个用户token登陆，则先在redis中判断token是否存在

        return new JwtToken(token);
    }

    /**
     * 访问失败处理
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ServerError error = new ServerError(HttpServletResponse.SC_UNAUTHORIZED, "invalid token");
        HttpServletResponseUtil.printJson(httpServletResponse, error);

        return false;
    }

    /**
     * 判断是否允许访问
     * <p>
     * 判定该请求是否允许访问，true: 则拦截器直接放行该请求; false: 将继续调用onAccessDenied方法
     * 1. 在url拦截规则中, 对于无需认证的url直接设置为anon(可匿名访问),故不会被该拦截器所拦截,
     * 2. 如果请求头中不含 token, 则返回false,拦截该请求;
     * 3 .否则调用 executeLogin 方法 以进行基于token的自定义令牌的认证
     * 4. 这里也可以可直接返回false,将是否需要进行认证放在 onAccessDenied 方法中去实现
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        String token = GetTokenUtil.getToken((HttpServletRequest) request);
        if (ObjectUtils.isEmpty(token)) {
            return false;
        }

        if (this.isLoginRequest(request, response)) {
            return true;
        }
        boolean allowed = false;
        try {
            allowed = executeLogin(request, response);
        } catch (Exception ignored) {
        }
        return allowed || super.isPermissive(mappedValue);
    }

    /**
     * 登陆成功处理
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) {
        return true;
    }

    /**
     * 可选地，用于向浏览器返回认证失败时的响应信息
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ServerError error = new ServerError(HttpServletResponse.SC_UNAUTHORIZED, "invalid token");
            HttpServletResponseUtil.printJson(httpServletResponse, error);
        } catch (Exception ex) {
            log.error("login failure:", ex);
        }

        return false;
    }
}