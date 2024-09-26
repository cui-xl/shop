package com.cxl.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.action.RestBuilderListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ElasticSearchConfig {
    @Value("${spring.elasticsearch.host}")
    String host;
    @Value("${spring.elasticsearch.port}")
    Integer port;
    @Bean
    RestHighLevelClient getClient(){
        return new RestHighLevelClient(
                RestClient.builder(new HttpHost(host,port))
        );
    }
}
