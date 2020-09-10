package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter
                = new CommonsRequestLoggingFilter();
        filter.setIncludeHeaders(true);
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        return filter;
    }

    @Bean
    public FilterRegistrationBean<GreetingFilter> greetingFilter() {
        FilterRegistrationBean<GreetingFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new GreetingFilter());
        registrationBean.addUrlPatterns("/greeting/*");

        return registrationBean;
    }
}
