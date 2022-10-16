package com.ideas2it.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmployeeInterceptor implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        return true;
    }

    /**
     * sample log output: Request Processing Time: 4ms for path /
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        request.removeAttribute("startTime");

        long endTime = System.currentTimeMillis();

        String path = null;
        if(StringUtils.isEmpty(request.getQueryString())) {
            path = request.getServletPath();
        }else {
            path = request.getServletPath() + "?" + request.getQueryString();
        }
        LOG.info("Request Processing Time: " + (endTime - startTime) + "ms for path "
                + path);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception exception)
            throws Exception
    {
        System.out.println("Inside afterCompletion!!");
    }
}