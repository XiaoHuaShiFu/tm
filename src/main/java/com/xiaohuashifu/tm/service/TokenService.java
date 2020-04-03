package com.xiaohuashifu.tm.service;

import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.result.Result;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
public interface TokenService {

    Result<TokenAO> saveToken(TokenAO tokenAO);

    Result<TokenAO> saveToken(TokenAO tokenAO, int seconds);

    Result<TokenAO> createAndSaveToken(TokenType tokenType, String username, String password);

    Result<TokenAO> createAndSaveTokenByCode(TokenType tokenType, String code);

    Result<TokenAO> getToken(String token);

    Result<TokenAO> getTokenAndAuthTokenTypeAndUpdateExpire(String token, TokenType[] tokenTypes, int seconds);

}
