package com.xiaohuashifu.tm.mvc.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

/**
 * 获取form提交时设置的header中的token
 * @author TAO
 * @date 2020/05/05
 */
public class HiddenHttpHeaderFilter extends OncePerRequestFilter {

	private static final String ALLOWED_HEADER_PREFIX = "authorization";
	
	/** Default header parameter: _header*/
	public static final String DEFAULT_HEADER_PARAM = "_header";

	private String headerParam = DEFAULT_HEADER_PARAM;
	
	
	public void setHeaderParam(String headerParam) {
		Assert.hasText(headerParam, "'headerParam' must not be empty");
		this.headerParam = headerParam;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		HttpServletRequest requestToUse = request;

		if ("POST".equals(request.getMethod()) && request.getAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE) == null) {
			String header = request.getParameter(this.headerParam);
			if (StringUtils.hasLength(header)) {
				if (header.startsWith(ALLOWED_HEADER_PREFIX)) {
					requestToUse = new HttpHeaderRequestWrapper(request, header.substring(ALLOWED_HEADER_PREFIX.length() + 1));
				}
			}
		}

		filterChain.doFilter(requestToUse, response);
	}
	
	
	private static class HttpHeaderRequestWrapper extends HttpServletRequestWrapper {

		private final String header;
		
		public HttpHeaderRequestWrapper(HttpServletRequest request, String header) {
			super(request);
			this.header = header;
		}

		@Override
		public String getHeader(String name) {
			if (ALLOWED_HEADER_PREFIX.equals(name)) {
				return this.header;
			}
			return super.getHeader(name);
		}
	}
	
}
