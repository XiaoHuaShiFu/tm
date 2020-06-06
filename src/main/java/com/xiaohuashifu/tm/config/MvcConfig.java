package com.xiaohuashifu.tm.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xiaohuashifu.tm.mvc.filter.HiddenHttpHeaderFilter;
import com.xiaohuashifu.tm.mvc.filter.VisitFilter;

/**
 * MVC配置
 * @author TAO
 * @date 2020/06/02
 */
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
        filterRegistrationBean.addUrlPatterns("/v1/admin/*");
        filterRegistrationBean.addUrlPatterns("/v1/attend/*");
        filterRegistrationBean.addUrlPatterns("/v1/login/*");
        filterRegistrationBean.addUrlPatterns("/v1/meeting/choose/qrcode");
        return filterRegistrationBean;
    }
	
	/**
	 * 用于外部对admin页面的GET访问重定向到登录页
	 * @return FilterRegistrationBean<VisitFilter>
	 */
	@Bean
    public FilterRegistrationBean<VisitFilter> preMostFilter() {
        FilterRegistrationBean<VisitFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new VisitFilter());
        filterRegistrationBean.addUrlPatterns("/v1/admin/*");
        return filterRegistrationBean;
    }
	
}
