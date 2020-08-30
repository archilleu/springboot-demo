package com.hoya.core.page;

import com.github.pagehelper.PageInfo;
import com.hoya.core.exception.AppExceptionServerError;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;

@SuppressWarnings({"all"})
public class PageHelper {
    private static final String findPage = "findPage";

    public static PageResult findPage(PageRequest pageRequest, Object mapper) {
        return findPage(pageRequest, mapper, findPage);
    }

    public static PageResult findPage(
            PageRequest pageRequest, Object mapper, String methodName, Object... args) {

        Method method = getMethod(mapper.getClass(), methodName, args);
        if (null == method) {
            throw new AppExceptionServerError("内部错误");
        }

        com.github.pagehelper.PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        List res = (List) ReflectionUtils.invokeMethod(method, mapper, args);

        PageInfo pageInfo = new PageInfo(res);
        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setTotalPages(pageInfo.getPages());
        pageResult.setData(pageInfo.getList());
        return pageResult;
    }

    private static Method getMethod(Class<?> clazz, String methodName, Object[] params) {
        Class<?> paramTypes[] = new Class<?>[params.length];
        for (int i = 0; i < params.length; i++) {
            paramTypes[i] = params[i].getClass();
        }
        Method method = ReflectionUtils.findMethod(clazz, methodName, paramTypes);
        if (null == method) {
            try {
                throw new NoSuchMethodException(clazz.getName() + " 类中没有找到 " + methodName + " 方法。");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        return method;
    }
}
