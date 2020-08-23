package com.hoya.core.page;

import com.github.pagehelper.PageInfo;
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

        Method method = ReflectionUtils.findMethod(mapper.getClass(), methodName);
        if (null == method) {
            try {
                throw new NoSuchMethodException(mapper.getClass().getName() + " 类中没有找到 " + method + " 方法。");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
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

}
