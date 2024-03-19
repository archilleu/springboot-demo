package com.example.common.base.vo;

import lombok.Data;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;

import javax.validation.constraints.NotNull;

/**
 * @author cjy
 */
@Data
public class PageRequest<T> {
    int page = 1;
    int size = 10;

    @NotNull
    @Validate
    private T params;
}
