package com.woniu.mvc;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 允许跨域访问
 */
@Configuration
public class RequestFilter implements Filter {

    @Bean
    public FilterRegistrationBean httpServletRequestReplacedRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RequestFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("httpServletRequestReplacedFilter");
        registration.setOrder(1);
        return registration;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        // 允许跨域访问
        HttpServletResponse corsResponse = (HttpServletResponse) response;
        HttpServletRequest corsRequest = (HttpServletRequest) request;

        corsResponse.setContentType("application/json; charset=utf-8");
        corsResponse.setCharacterEncoding("UTF-8");
        corsResponse.setHeader("Access-Control-Max-Age", "3600");
        corsResponse.setHeader("Access-Control-Allow-Methods", "POST, GET,PUT, OPTIONS, DELETE");
        corsResponse.setHeader("Access-Control-Allow-Origin", corsRequest.getHeader("Origin"));
        corsResponse.setHeader("Access-Control-Allow-Credentials", "true");
        corsResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, token");

        chain.doFilter(request, corsResponse);
    }
}
