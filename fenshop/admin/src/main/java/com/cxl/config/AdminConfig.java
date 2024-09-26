package com.cxl.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class AdminConfig {
    @Bean
    BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    MybatisPlusInterceptor getInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
    @Bean
    Converter<String, LocalDate> getLocalDate(){
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String s) {
                return LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        };
    }
//    @Bean
//    CorsFilter getCorsFilter(){
//        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
//
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("http://127.0.0.1");
//        configuration.addAllowedOrigin("http://localhost");
//        configuration.addAllowedMethod("*");
//        configuration.addAllowedHeader("*");
//        source.registerCorsConfiguration("/**",configuration);
//        return new CorsFilter(source);
//    }

}
