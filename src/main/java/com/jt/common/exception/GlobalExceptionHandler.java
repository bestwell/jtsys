package com.jt.common.exception;

import com.jt.common.vo.JsonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**全局异常处理类*/
@ControllerAdvice
public class GlobalExceptionHandler {
    //jdk自带的日志API
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public JsonResult doHandlerRuntimeException(RuntimeException e){
        e.printStackTrace();
        return new JsonResult(e);//封装异常信息
    }

}
