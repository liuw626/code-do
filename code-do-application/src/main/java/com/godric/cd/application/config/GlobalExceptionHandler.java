package com.godric.cd.application.config;

import com.godric.cd.result.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public BaseResult bindExceptionHandler(BindException e, HttpServletRequest request) {
        String failMsg = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        log.error("BindException, URL:{} ,msg:{} ", request.getRequestURI(), failMsg);
        return BaseResult.fail(failMsg);
    }

}
