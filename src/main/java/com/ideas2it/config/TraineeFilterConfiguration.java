package com.ideas2it.config;

import com.ideas2it.filter.TraineeFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TraineeFilterConfiguration {
    @Bean
    public FilterRegistrationBean<TraineeFilter> traineeRegistrationBean() {
        FilterRegistrationBean<TraineeFilter> traineeFilterRegistrationBean = new FilterRegistrationBean<TraineeFilter>();
        traineeFilterRegistrationBean.setFilter(new TraineeFilter());
        traineeFilterRegistrationBean.addUrlPatterns("/employee_portal/*");
        return traineeFilterRegistrationBean;
    }
}

