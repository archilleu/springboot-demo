package com.example.common.base.advice;

import com.example.common.base.annotation.EnableGlobalResultResponse;
import com.example.common.base.annotation.IgnoreGlobalResultDispose;
import com.example.common.base.config.GlobalDefaultProperties;
import com.example.common.base.vo.HttpResult;
import com.example.common.base.vo.ResultCode;
import com.example.common.base.vo.ServerError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * {@link EnableGlobalResultResponse} 处理解析 {@link GlobalResultResponseAdvice}数据包装器
 *
 * @author cjy
 */

@RestControllerAdvice
@SuppressWarnings("unchecked")
public class GlobalResultResponseAdvice implements ResponseBodyAdvice<Object> {

    final private GlobalDefaultProperties globalDefaultProperties;

    public GlobalResultResponseAdvice(GlobalDefaultProperties globalDefaultProperties) {
        this.globalDefaultProperties = globalDefaultProperties;
    }

    @Override
    @SuppressWarnings("all")
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        return filter(methodParameter);
    }

    @Nullable
    @Override
    @SuppressWarnings("all")
    public Object beforeBodyWrite(Object resp, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        if (resp instanceof HttpResult) {
            return resp;
        }

        if (resp instanceof ServerError) {
            return resp;
        }

        /**
         * string 特殊处理
         */
        if (resp instanceof String) {
            try {
                if (((String) resp).startsWith("{")) {
                    return resp;
                }

                ObjectMapper om = new ObjectMapper();
                return om.writeValueAsString(new HttpResult(ResultCode.OK, resp));
            } catch (Exception e) {
            }
        }

        HttpResult result = new HttpResult(ResultCode.OK, resp);
        return result;
    }

    private Boolean filter(MethodParameter methodParameter) {
        // 检查注解是否存在
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreGlobalResultDispose.class)) {
            return false;
        }
        if (Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(IgnoreGlobalResultDispose.class)) {
            return false;
        }

        Class<?> declaringClass = methodParameter.getDeclaringClass();
        // 检查过滤包路径
        long count = globalDefaultProperties.getAdvicePackage().stream()
                .filter(l -> declaringClass.getName().contains(l)).count();
        if (count > 0) {
            return true;
        }

        // 检查<类>过滤列表
        return globalDefaultProperties.getAdviceClass().contains(declaringClass.getName());
    }
}
