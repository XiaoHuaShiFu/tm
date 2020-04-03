package com.xiaohuashifu.tm.aspect;

import com.xiaohuashifu.tm.aspect.annotation.TokenAuth;
import com.xiaohuashifu.tm.constant.TokenExpire;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.result.ErrorResponse;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.TokenService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述: 身份认证切面
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 22:22
 */
@Aspect
@Component
public class TokenAuthAspect {

    private final TokenService tokenService;

    @Autowired
    public TokenAuthAspect(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * 认证token令牌
     *
     * @param joinPoint ProceedingJoinPoint
     * @param request HttpServletRequest
     * @return Object
     * 会把TokenAO设置在request.attribute.tokenAO里
     *
     * @throws Throwable .
     *
     * @errors:
     * UNAUTHORIZED
     * UNAUTHORIZED_TOKEN_IS_NULL
     * FORBIDDEN_SUB_USER
     */
    @Around(value = "@annotation(com.xiaohuashifu.tm.aspect.annotation.TokenAuth) && @annotation(tokenAuth) && args(request, ..)")
    public Object authToken(ProceedingJoinPoint joinPoint, HttpServletRequest request,
                            TokenAuth tokenAuth) throws Throwable {
        String token = request.getHeader("authorization");
        // 尝试在session里获取token
        if(token == null) {
        	token = (String) request.getSession().getAttribute("token");
        }
        // token不在头部
        if (token == null) {
            ErrorResponse errorResponse = new ErrorResponse(ErrorCode.UNAUTHORIZED_TOKEN_IS_NULL.getError(),
                    ErrorCode.UNAUTHORIZED_TOKEN_IS_NULL.getMessage());
            return new ResponseEntity<>(errorResponse, ErrorCode.UNAUTHORIZED_TOKEN_IS_NULL.getHttpStatus());
        }

        TokenType[] tokenTypes = tokenAuth.tokenType();
        Result<TokenAO> result = tokenService.getTokenAndAuthTokenTypeAndUpdateExpire(token, tokenTypes,
                TokenExpire.NORMAL.getExpire());
        if (!result.isSuccess()) {
            ErrorResponse errorResponse = new ErrorResponse(result.getErrorCode().getError(),
                    result.getErrorCode().getMessage());
            return new ResponseEntity<>(errorResponse, result.getErrorCode().getHttpStatus());
        }

        // 把此id传递给控制器
        request.setAttribute("tokenAO", result.getData());
        return joinPoint.proceed();
    }

}
