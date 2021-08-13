package com.starter.common.service;

import com.starter.common.exception.ServerException;
import com.starter.common.page.PageRequest;
import com.starter.common.page.PageResult;

import java.util.List;

public interface CurdService<T> {

    /**
     * 保存操作
     *
     * @param record
     * @return
     */
    default int save(T record) {
        throw ServerException.Forbidden;
    }

    /**
     * 删除操作
     *
     * @param record
     * @return
     */
    default int delete(T record) {
        throw ServerException.Forbidden;
    }

    /**
     * 批量删除操作
     *
     * @param records
     */
    default int delete(List<T> records) {
        throw ServerException.Forbidden;
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    default T findById(Long id) {
        throw ServerException.Forbidden;
    }

    /**
     * 根据key查询
     *
     * @param key
     * @return
     */
    default T findByKey(String key) {
        throw ServerException.Forbidden;
    }

    /**
     * 分页查询
     *
     * @param pageRequest
     * @return PageResult
     */
    default PageResult findPage(PageRequest pageRequest) {
        throw ServerException.Forbidden;
    }

    /**
     * 全部数据
     *
     * @return
     */
    default List<T> list() {
        throw ServerException.Forbidden;
    }

}
