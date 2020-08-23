package com.hoya.core.page;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class PageRequest {
    /**
     * 当前页码
     */
    private int pageNum = 1;

    /**
     * 每页数量
     */
    private int pageSize = 10;

    /**
     * 查询参数
     */
    private Map<String, Object> params = new HashMap<>();

    /**
     * 设置参数值
     * @param params 参数
     */
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    /**
     * 获取参数key值
     * @param key
     * @return value值
     */
    public Object getParam(String key) {
        return getParams().get(key);
    }
}
