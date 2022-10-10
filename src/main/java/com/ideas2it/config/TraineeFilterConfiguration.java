package com.ideas2it.config;

import com.ideas2it.filter.TraineeFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TraineeFilterConfiguration {

    @Bean
    public FilterRegistrationBean<TraineeFilter> registrationBean() {
        FilterRegistrationBean<TraineeFilter> registrationBean = new FilterRegistrationBean<TraineeFilter>();
        registrationBean.setFilter(new TraineeFilter());
        registrationBean.addUrlPatterns("/employee_portal/*");
        return registrationBean;
    }
}
