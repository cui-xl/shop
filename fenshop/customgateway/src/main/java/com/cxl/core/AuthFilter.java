package com.cxl.core;

import com.alibaba.fastjson.JSONObject;
import com.cxl.entity.UmsCustomer;
import com.cxl.util.Entityutil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "spring.auth")
public class AuthFilter implements GlobalFilter, Ordered {
    List<String> urls;
    public List<String> getUrls() {
        return urls;
    }
    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        //获取request和response
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //浏览器当前的url
        URI uri = request.getURI();
        String path = uri.getPath();
        //循环要检验的地址
        for (String url:urls){
            //如果能匹配的上需要拦截的网页的话，则本次请求需要登陆
         if (pathMatcher.match(url,path)){
             List<String> list = request.getHeaders().get("TOKEN");
             if (list==null || list.size()==0){
                 return this.error(response,ResultJson.unlogin("未登录"));
             }
             String token = list.get(0);
             try {
                 UmsCustomer customer = Entityutil.JwtCustomerDecoder(token);
                 String query = uri.getQuery();
                 StringBuilder builder=new StringBuilder();
                 if (StringUtils.isNotBlank(query)){
                     builder.append(query).append("&");
                 }
                 builder.append("open_id=").append(customer.getId())
                         .append("&open_name=").append(customer.getNickyName());
                 URI new_uri = UriComponentsBuilder.fromUri(uri).replaceQuery(builder.toString()).build().toUri();
                 ServerHttpRequest new_request = request.mutate().uri(new_uri).build();
                 ServerWebExchange new_exchange = exchange.mutate().request(new_request).build();
                 return chain.filter(new_exchange);
             }catch (Exception ex){
                 ex.printStackTrace();
                 System.out.println("token是乱写的");
                 return this.error(response,ResultJson.unlogin("非法请求，token是错的"));
             }
         }
        }
        System.out.println(urls);
        return chain.filter(exchange);
    }
    @Override
    public int getOrder() {
        return 0;
    }
    public Mono<Void> error(ServerHttpResponse response,ResultJson resultJson){
        response.getHeaders().set("Content-type","application/json:charset=uft-8");
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSONObject.toJSONString(resultJson).getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Flux.just(dataBuffer));
    }
}
