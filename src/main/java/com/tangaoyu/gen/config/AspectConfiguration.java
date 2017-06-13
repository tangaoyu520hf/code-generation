package com.tangaoyu.gen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.validation.Validator;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfiguration {
    @Bean
    public ValidatorAspect loggingAspect(Validator validator) {
        return new ValidatorAspect(validator);
    }
}

