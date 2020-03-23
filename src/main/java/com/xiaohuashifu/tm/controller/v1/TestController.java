package com.xiaohuashifu.tm.controller.v1;

import com.xiaohuashifu.tm.dao.TestMapper;
import com.xiaohuashifu.tm.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    private final TestMapper testMapper;

    private final FileService fileService;

    private final RedisTemplate redisTemplate;

    @Autowired
    public TestController(TestMapper testMapper, FileService fileService, RedisTemplate redisTemplate) {
        this.testMapper = testMapper;
        this.fileService = fileService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 测试MySQL
     * @return
     */
    @RequestMapping("/test")
    public Object test() {
        return testMapper.selectAll();
    }

    /**
     * 测试SpringMVC
     * @return
     */
    @RequestMapping("/test1")
    public Object test1() {
        return "zzzzzzzzzzzds";
    }

    /**
     * 测试FTP
     * @param file
     * @return
     */
    @RequestMapping("/test2")
    public Object saveFile(MultipartFile file) {
        return fileService.save(file, "user");
    }

    /**
     * 测试Redis
     * @param key
     * @param value
     * @return
     */
    @RequestMapping("/test3")
    public Object test3(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return redisTemplate.opsForValue().get(key);
    }


}
