package top.nomelin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.nomelin.model.Result;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleInternalError(Exception ex) {
        ex.printStackTrace();
        log.error("服务器内部错误", ex);
        return new Result("400", "设备模拟服务器：" + ex.getMessage());
    }
}
