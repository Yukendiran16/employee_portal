package com.javapointers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author Jerry Conde, webmaster@javapointers.com
 * @since 8/9/2016
 */
@Configuration
public class BeanConfigurations {

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandlerImpl();
    }
}
