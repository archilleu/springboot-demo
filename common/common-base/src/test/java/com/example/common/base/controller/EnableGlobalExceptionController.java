package com.example.common.base.controller;

/**
 * {@link EnableGlobalExceptionHandle}返回包装器测试
 */

import com.example.common.base.exception.ResultCode;
import com.example.common.base.exception.ServerException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class EnableGlobalExceptionController {
    @GetMapping("/server-exception")
    public void serverException() {
        throw new ServerException(ResultCode.BAD_REQUEST, "bad request");
    }

    @PostMapping("/http-message-not-readable-exception")
    public void httpMessageNotReadableException(@RequestBody @Validated TestModel testModel) {
        return;
    }

    @PostMapping("/illegal-argument-exception")
    public void illegalArgumentException(@RequestBody @Validated TestModel testModel, BindingResult bindingResult) {
    }

    @PostMapping("/illegal-argument-exception1")
    public void illegalArgumentException1(@RequestParam String must) {
        return;
    }

    @PostMapping("/error-http-method-exception")
    public void errorHttpMethodException() throws HttpRequestMethodNotSupportedException {
    }

    @PostMapping("/database-exception")
    public void databaseException() throws SQLException {
        throw new SQLException("database error");
    }

}
