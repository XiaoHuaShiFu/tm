package com.xiaohuashifu.tm.controller.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-03-16 23:55
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/test")
    public Object test() {
        return "ddddzz";
    }

}
