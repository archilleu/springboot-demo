package com.starter.common.page;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.starter.common.exception.ServerExceptionForbidden;
import com.starter.common.exception.ServerExceptionServerError;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"all"})
public class PageHelper {

    private static final String findPage = "findPage";

    public static PageResult findPage(PageRequest pageRequest, Object mapper) {
        return findPage(pageRequest, mapper, findPage, null);
    }

    public static PageResult findPage(PageRequest pageRequest, Object mapper, Map<String, Object> args) {
        return findPage(pageRequest, mapper, findPage, args);
    }

    public static <T> PageResult findPage(PageRequest pageRequest, IService<T> service) {
        checkPage(pageRequest);

        com.github.pagehelper.PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List list = service.list();
        PageInfo pageInfo = new PageInfo(list);
        PageResult pageResult = new PageResult();
        pageResult.setPage(pageInfo.getPageNum());
        pageResult.setRows(pageInfo.getPageSize());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setTotalPages(pageInfo.getPages());
        pageResult.setContent(pageInfo.getList());
        return pageResult;
    }

    public static <T> PageResult findPage(PageRequest pageRequest, IService<T> service, Wrapper<T> queryWrapper) {
        checkPage(pageRequest);

        com.github.pagehelper.PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List list = service.list(queryWrapper);
        PageInfo pageInfo = new PageInfo(list);
        PageResult pageResult = new PageResult();
        pageResult.setPage(pageInfo.getPageNum());
        pageResult.setRows(pageInfo.getPageSize());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setTotalPages(pageInfo.getPages());
        pageResult.setContent(pageInfo.getList());
        return pageResult;
    }

    public static PageResult findPage(PageRequest pageRequest, Object mapper, String methodName, Map<String, Object> args) {
        checkPage(pageRequest);

        Method method = ReflectionUtils.findMethod(mapper.getClass(), methodName, Map.class);
        if (null == method) {
            throw new ServerExceptionServerError(mapper.getClass().getName() + " not found methom:" + methodName);
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

    private static void checkPage(PageRequest pageRequest) {
        if (pageRequest.getPage() <= 0) {
            throw new ServerExceptionForbidden("page must > 0!");
        }
        if (pageRequest.getRows() > 100) {
            throw new ServerExceptionForbidden("rows too large!");
        }
        if (pageRequest.getRows() <= 0) {
            throw new ServerExceptionForbidden("rows must > 0!");
        }
    }
}
