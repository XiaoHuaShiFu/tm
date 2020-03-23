package com.xiaohuashifu.tm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述: MultipartConfig
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-11 22:04
 */
@Configuration
public class MultipartConfig {

    /**
     * 配置MultipartResolver
     * @return MultipartResolver
     */
    @Bean
    public MultipartResolver multipartResolver() {
        //这里必须修改，否则除post和get外的带MultipartFile的请求将报错
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver() {
            @Override
            public boolean isMultipart(HttpServletRequest request) {
                final String header = request.getHeader("Content-Type");
                if (header == null) {
                    return false;
                }
                return header.contains("multipart/form-data");
            }
        };
        commonsMultipartResolver.setMaxUploadSize(10485760);
        commonsMultipartResolver.setMaxInMemorySize(4096);
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        return commonsMultipartResolver;
    }

}
