package ink.eccentric.handler.exception;

import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.enums.AppHttpCodeEnum;
import ink.eccentric.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 * @author eccentric
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e){
        log.error("出现异常: {}",e.getMessage());
        if (e instanceof SystemException){
            SystemException se = (SystemException) e;
            return Result.fail(se.getCode(), se.getMsg());
        }
        return Result.fail(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }


}
