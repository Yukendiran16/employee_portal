package com.ideas2it.filter;

import org.hibernate.annotations.common.reflection.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestResponseLoggingFilter {
    private static final Logger LOG = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);


    public void init(FilterConfig filterConfig)  {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        LOG.info("Logging Request  {} : {}", req.getMethod(), req.getRequestURI());

        chain.doFilter(request, response);

        LOG.info("Logging Response :{}", res.getContentType());
    }

    public void destroy() {
    }
}