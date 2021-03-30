package com.hoya.core.page;

import com.github.pagehelper.PageInfo;
import com.hoya.core.exception.ServerExceptionServerError;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;

@SuppressWarnings({"all"})
public class PageHelper {
    private static final String findPage = "findPage";

    public static PageResult findPage(PageRequest pageRequest, Object mapper) {
        return findPage(pageRequest, mapper, findPage);
    }

    public static PageResult findPage(PageRequest pageRequest, Object mapper, Object... args) {
        return findPage(pageRequest, mapper, findPage, args);
    }

    public static PageResult findPage(
            PageRequest pageRequest, Object mapper, String methodName, Object... args) {

        Method method = getMethod(mapper.getClass(), methodName, args);
        if (null == method) {
            throw new ServerExceptionServerError("内部错误");
        }

        com.github.pagehelper.PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List res = (List) ReflectionUtils.invokeMethod(method, mapper, args);

        PageInfo pageInfo = new PageInfo(res);
        PageResult pageResult = new PageResult();
        pageResult.setPage(pageInfo.getPageNum());
        pageResult.setRows(pageInfo.getPageSize());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setTotalPages(pageInfo.getPages());
        pageResult.setContent(pageInfo.getList());
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
