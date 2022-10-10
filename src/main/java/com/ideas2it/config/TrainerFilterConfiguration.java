package com.ideas2it.config;

import com.ideas2it.filter.TrainerFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrainerFilterConfiguration {

    @Bean
    public FilterRegistrationBean<TrainerFilter> registrationBean() {
        FilterRegistrationBean<TrainerFilter> registrationBean = new FilterRegistrationBean<TrainerFilter>();
        registrationBean.setFilter(new TrainerFilter());
        registrationBean.addUrlPatterns("/employee_portal/*");
        return registrationBean;
    }
}