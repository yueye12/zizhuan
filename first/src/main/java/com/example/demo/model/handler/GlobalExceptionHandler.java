package com.example.demo.model.handler;

import com.example.demo.model.constant.MessageConstant;
import com.example.demo.model.exception.BaseException;
import com.example.demo.model.exception.TokenExpiredException;
import com.example.demo.model.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex) {
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 处理SQL异常
     *
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
        String message = sqlIntegrityConstraintViolationException.getMessage();
        if (message.contains("Duplicate entry")) {
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + MessageConstant.ALREADY_EXISTS;
            return Result.error(msg);
        } else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result handleMissingServletRequestParameterException(MissingServletRequestParameterException missingServletRequestParameterException) {
        String parameterName = missingServletRequestParameterException.getParameterName();
        return Result.error( "没有参数" + parameterName);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public Result handleTokenExpiredException(TokenExpiredException tokenExpiredException) {
        return Result.error("Token过期或不存在");
    }
}
