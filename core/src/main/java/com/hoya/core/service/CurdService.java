package com.hoya.core.service;

import java.util.List;

import com.hoya.core.page.PageRequest;
import com.hoya.core.page.PageResult;

public interface CurdService<T> {
	
	/**
	 * 保存操作
	 * @param record
	 * @return
	 */
	default int save(T record) {
		return 0;
	}
	
	/**
	 * 删除操作
	 * @param record
	 * @return
	 */
	default int delete(T record) {
		return 0;
	}
	
	/**
	 * 批量删除操作
	 * @param records
	 */
	default int delete(List<T> records) {
		return 0;
	}
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	default T findById(Long id) {
		return null;
	}
	
    /**
     * 分页查询
	 * @param pageRequest
	 * @return PageResult
     */
	default PageResult findPage(PageRequest pageRequest) {
	    return null;
	}
	
}