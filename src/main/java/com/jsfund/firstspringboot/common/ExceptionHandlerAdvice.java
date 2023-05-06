package com.jsfund.firstspringboot.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

/**
 * 全局异常处理类
 * @author Administrator
 * @create 2023/4/30 16:02
 */
//可拦截项目中抛出的异常，如参数格式异常、参数缺失、系统异常等
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    // 参数缺失异常处理
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseInfo badRequestException(Exception exception) {
        log.error("参数缺失异常处理：" + exception.getMessage());
        return new ResponseInfo(HttpStatus.BAD_REQUEST.value(), "缺少必填参数！");
    }

    // 参数格式异常处理
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseInfo badRequestException(IllegalArgumentException exception) {
        log.error("参数格式不合法：" + exception.getMessage());
        return new ResponseInfo(HttpStatus.BAD_REQUEST.value(), "参数格式不符！");
    }

    // 权限不足异常处理
    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseInfo badRequestException(AccessDeniedException exception) {
        log.error("权限不足异常处理：" + exception.getMessage());
        return new ResponseInfo(HttpStatus.FORBIDDEN.value(), exception.getMessage());
    }

    // 空指针异常
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseInfo handleTypeMismatchException(NullPointerException ex) {
        log.error("空指针异常，{}", ex.getMessage());
        return new ResponseInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), "空指针异常");
    }

}
