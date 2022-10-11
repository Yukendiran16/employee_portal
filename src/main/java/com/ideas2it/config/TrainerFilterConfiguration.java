package com.ideas2it.config;

import com.ideas2it.exception.BadUrlPatternException;
import com.ideas2it.filter.TrainerFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class TrainerFilterConfiguration {
    @Bean
    public FilterRegistrationBean<TrainerFilter> trainerRegistrationBean() {
        FilterRegistrationBean<TrainerFilter> trainerFilterRegistrationBean = new FilterRegistrationBean<TrainerFilter>();
        trainerFilterRegistrationBean.setFilter(new TrainerFilter());
        trainerFilterRegistrationBean.addUrlPatterns("/employee_portal/*");
        return trainerFilterRegistrationBean;
    }

}
