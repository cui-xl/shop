package com.cxl.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@MapperScan("com.cxl.mapper")
public class ProductConfig {
//    @Bean
//    CorsFilter corsFilter(){
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.addAllowedOrigin("http://127.0.0.1");
//        corsConfiguration.addAllowedOrigin("http://localhost");
//        corsConfiguration.addAllowedHeader("*");
//        source.registerCorsConfiguration("/**",corsConfiguration);
//        return new CorsFilter(source);
//
//    }
    @Bean
    MybatisPlusInterceptor getInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
