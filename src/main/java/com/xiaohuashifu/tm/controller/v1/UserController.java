package com.xiaohuashifu.tm.controller.v1;

import com.xiaohuashifu.tm.aspect.annotation.ErrorHandler;
import com.xiaohuashifu.tm.service.FileService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述: 用户模块
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-03-16 23:55
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    private final FileService fileService;

    private final Mapper mapper;

    @Autowired
    public UserController(FileService fileService, Mapper mapper) {
        this.fileService = fileService;
        this.mapper = mapper;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ErrorHandler
    public Object post() {
        return "dasda";
    }

}
