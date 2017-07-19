package com.tangaoyu.gen.config;

import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lorne on 2017/6/26.
 */

@Configuration
@ConditionalOnMissingBean(HystrixConcurrencyStrategy.class)
public class TransactionContextConfiguration {
    @Bean
    public HystrixConcurrencyStrategy hystrixConcurrencyStrategy() {
        return new CustomHystrixConcurrencyStrategy();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new TransactionRestTemplateInterceptor();
    }
}
