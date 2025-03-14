package top.nomelin.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.nomelin.model.Result;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleInternalError(Exception ex) {
        return new Result("400", "Internal Server Error", ex.getMessage());
    }
}
