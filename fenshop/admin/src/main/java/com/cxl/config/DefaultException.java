package com.cxl.config;

import com.cxl.core.ResultJson;
import com.cxl.exception.CxlException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultException {
    @ExceptionHandler
    ResultJson defaultExceptionHandler(Exception ex){
        ex.printStackTrace();
        if (ex instanceof CxlException){
            return ResultJson.failed(ex.getMessage());
        }
        return ResultJson.failed("请联系管理员，出错啦");
    }

}
