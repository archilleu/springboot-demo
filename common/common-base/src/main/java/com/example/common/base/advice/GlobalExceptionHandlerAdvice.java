package com.example.common.base.advice;

import com.example.common.base.annotation.EnableGlobalExceptionHandle;
import com.example.common.base.exception.ResultCode;
import com.example.common.base.exception.ServerError;
import com.example.common.base.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import java.sql.SQLException;

/**
 * {@link GlobalExceptionHandlerAdvice}全局异常处理,处理 {@link EnableGlobalExceptionHandle}注解
 *
 * @author cjy
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerAdvice {

    /**
     * 自定义的Server异常
     */
    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ServerError> exception(ServerException ex) {
        ServerError error = new ServerError(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.valueOf(ex.getCode() / 10));
    }

    /**
     * 参数错误异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ServerError> exception(IllegalArgumentException ex) {
        ServerError error = new ServerError(ResultCode.BAD_REQUEST.getCode(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * 缺少参数
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ServerError> exception(MissingServletRequestParameterException ex) {
        ServerError error = new ServerError(ResultCode.BAD_REQUEST.getCode(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * 不是合法的文件
     */
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ServerError> exception(MultipartException ex) {
        ServerError error = new ServerError(ResultCode.BAD_REQUEST.getCode(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * json格式错误异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ServerError> exception(HttpMessageNotReadableException ignoredEx) {
        ServerError error = new ServerError(ResultCode.BAD_REQUEST.getCode(), "json序列化失败，请检查json语法和参数");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * 捕获controller{@link Validated} 请求入参校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ServerError> exception(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField());
            sb.append(" ");
            sb.append(fieldError.getDefaultMessage());
            sb.append(";");
        }
        ServerError error = new ServerError(ResultCode.BAD_REQUEST.getCode(), sb.toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ServerError> exception(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField());
            sb.append(" ");
            sb.append(fieldError.getDefaultMessage());
            sb.append(";");
        }
        ServerError error = new ServerError(ResultCode.BAD_REQUEST.getCode(), sb.toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * HTTP方法不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ServerError> exception(HttpRequestMethodNotSupportedException ignoredEx) {
        ServerError error = new ServerError(ResultCode.METHOD_NOT_ALLOWED.getCode(), "不支持的HTTP方法");
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * 数据库异常
     */
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ServerError> exception(SQLException ex) {
        log.error("database error:", ex);

        ServerError error = new ServerError(ResultCode.DATABASE_ERROR.getCode(), ResultCode.DATABASE_ERROR.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 权限校验异常
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ServerError> exception() {
        ServerError error = new ServerError(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    /**
     * 全局未定义异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServerError> exception(Exception ex) {
        log.error("server error:", ex);

        ServerError error = new ServerError(ResultCode.INTERNAL_SERVER_ERROR.getCode(), ResultCode.INTERNAL_SERVER_ERROR.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}