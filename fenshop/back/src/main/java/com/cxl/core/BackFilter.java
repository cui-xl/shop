package com.cxl.core;

import com.alibaba.fastjson.JSONObject;
import com.cxl.entity.UmsAdmin;
import com.cxl.util.Entityutil;
import com.netflix.loadbalancer.Server;
import com.sun.xml.internal.bind.api.impl.NameConverter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class BackFilter implements GlobalFilter, Ordered {
    @Resource(name = "rt")
    RedisTemplate redisTemplate;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //地址匹配器
        AntPathMatcher pathMatcher= new AntPathMatcher();
        //获取request和response
        System.out.println("进入过滤器");
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //获取请求地址
        URI uri = request.getURI();
        String path = uri.getPath();
//        System.out.println(path);
        //是请求的是后端登陆地址的话直接通过
        if (pathMatcher.match("/umsAdmin/login",path)){
            return chain.filter(exchange);
        }
        List<String> list = request.getHeaders().get("token");
        System.out.println(list);
        //如果没有token就不能通过
        if (list == null||list.size()==0){
            return this.error(response,ResultJson.unlogin("请求超时，请重新登录"));
        }
        String token = list.get(0);
        UmsAdmin umsAdmin = null;
        try {
            umsAdmin = Entityutil.JwtDecoder(token);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("非法请求");
        }
        //超时则重新登陆
        if (!redisTemplate.hasKey(umsAdmin.getUid())){
            System.out.println("请求超时,重新登陆");
        }
        redisTemplate.expire(umsAdmin.getUid(),30, TimeUnit.DAYS);
        String s =String.valueOf(redisTemplate.opsForValue().get(umsAdmin.getUid())) ;
        List<String> backUrls = JSONObject.parseArray(s, String.class);
        for (String url:backUrls){
            if (pathMatcher.match(url,path)){
                return chain.filter(exchange);
            }
        }
        return this.error(response,ResultJson.forbid("无权限"));
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
