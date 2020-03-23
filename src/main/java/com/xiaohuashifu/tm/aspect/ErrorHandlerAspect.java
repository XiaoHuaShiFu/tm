package com.xiaohuashifu.tm.aspect;

import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.result.ErrorResponse;
import com.xiaohuashifu.tm.result.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * 描述: 对返回结果的错误进行解析
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-19 22:47
 */
@Aspect
@Component
public class ErrorHandlerAspect {

    /**
     * 对返回结果的错误进行解析
     *
     * @param joinPoint ProceedingJoinPoint
     * @return Object
     */
    @Around(value = "@annotation(com.xiaohuashifu.tm.aspect.annotation.ErrorHandler)")
    public Object handler(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret = joinPoint.proceed();
        if (!(ret instanceof Result)) {
            return ret;
        }

        Result result = (Result) ret;
        if (result.isSuccess()) {
            return result;
        }

        ErrorCode errorCode = result.getErrorCode();
        String message = result.getMessage();
        ErrorResponse errorResponse;
        if (message == null) {
            errorResponse = new ErrorResponse(errorCode.getError(), errorCode.getMessage());
        } else {
            errorResponse = new ErrorResponse(errorCode.getError(), message);
        }
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

}
