package com.starter.common.controller;

/**
 * {@link EnableGlobalResultResponse }返回包装器测试
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnableGlobalResultResponseController {
    public static final Integer STANDARD_INT = 10;
    public static final String STANDARD_STRING = "hello world";
    public static final TestModel STANDARD_OBJECT = new TestModel("hello world", 10);

    @GetMapping("/void")
    public void voidSuccess() {
        return;
    }

    @GetMapping("/int")
    public Integer intSuccess() {
        return STANDARD_INT;
    }

    @GetMapping("/string")
    public String stringSuccess() {
        return STANDARD_STRING;
    }

    @GetMapping("object")
    public TestModel objectSuccess() {
        return STANDARD_OBJECT;
    }
}
