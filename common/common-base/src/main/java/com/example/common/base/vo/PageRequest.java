package com.example.common.base.vo;

import lombok.Data;

import javax.validation.Valid;

/**
 * @author cjy
 */
@Data
public class PageRequest<T> {
    int page = 1;
    int size = 10;
    @Valid
    private T params;
}
