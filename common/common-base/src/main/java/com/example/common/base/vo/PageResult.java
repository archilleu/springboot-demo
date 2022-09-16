package com.example.common.base.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * @author cjy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    private int page;
    private int size;
    private long total;
    private List<T> records;

    public static <T> PageResult<T> emptyInstance(PageRequest<T> page) {
        return new PageResult<T>(page.getPage(), page.getSize(), 0L, Collections.emptyList());
    }

    public static <T> PageResult<T> newInstance(IPage<T> page) {
        return new PageResult<T>((int) page.getCurrent(), (int) page.getSize(), page.getTotal(), page.getRecords());
    }
}
