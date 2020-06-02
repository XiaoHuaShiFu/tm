package com.xiaohuashifu.tm.mvc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 访问过滤，无权限时重定向到登录页面
 * @author TAO
 * @date 2020/06/02
 */
public class VisitFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest)request;
			if (httpRequest.getHeader("authorization") == null && httpRequest.getMethod().equals("GET")) {
				HttpServletResponse httpResponse = (HttpServletResponse)response;
				if (httpRequest.getRequestURI().indexOf("/v1/admin/login") == -1) {
					httpResponse.sendRedirect("/v1/admin/login");
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}

}
