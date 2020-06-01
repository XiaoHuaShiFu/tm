package com.xiaohuashifu.tm.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xiaohuashifu.tm.mvc.filter.HiddenHttpHeaderFilter;

@Configuration
public class MvcConfig {

	/**
	 * 用于获取form中的header
	 * @return FilterRegistrationBean<HiddenHttpHeaderFilter>
	 */
	@Bean
    public FilterRegistrationBean<HiddenHttpHeaderFilter> headerFilter() {
        FilterRegistrationBean<HiddenHttpHeaderFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new HiddenHttpHeaderFilter());
        List<String> urls = new ArrayList<>();
        urls.add("/v1/admin/*");
        filterRegistrationBean.setUrlPatterns(urls);
        return filterRegistrationBean;
    }
	
}
