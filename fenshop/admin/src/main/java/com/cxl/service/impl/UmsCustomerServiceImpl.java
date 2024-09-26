package com.cxl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxl.email.EmailBean;
import com.cxl.entity.UmsCustomer;
import com.cxl.exception.CxlException;
import com.cxl.mapper.UmsCustomerMapper;
import com.cxl.service.UmsCustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxl.util.Entityutil;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 客户表 服务实现类
 * </p>
 *
 * @author cxl
 * @since 2023-12-07
 */
@Service
public class UmsCustomerServiceImpl extends ServiceImpl<UmsCustomerMapper, UmsCustomer> implements UmsCustomerService {
    @Resource
    RabbitTemplate rabbitTemplate;
    @Resource(name = "rt")
    RedisTemplate redisTemplate;
    @Resource
    PasswordEncoder passwordEncoder;
    @Override
    public UmsCustomer get(String username) {
        QueryWrapper<UmsCustomer> wrapper=new QueryWrapper<>();
        wrapper.eq("email",username)
                .or()
                .eq("phone",username);
        UmsCustomer customer = this.getOne(wrapper);
        if (null==customer){
            throw new CxlException("手机号或者密码不对");
        }
        return customer;
    }

    @Override
    public Boolean sendCode(String username) {
        UmsCustomer customer = this.get(username);
        String code= RandomStringUtils.random(6,false,true);
        StringBuilder builder = new StringBuilder("本次登录的验证码")
                .append("<strong>"+code+"</strong>");
        EmailBean emailBean = new EmailBean(
                customer.getEmail(),
                "系统登陆",
                builder.toString()
        );
        redisTemplate.opsForValue().set(customer.getEmail(),code,5, TimeUnit.MINUTES);
        rabbitTemplate.convertAndSend("cxl.exchange","emailkey", JSONObject.toJSONString(emailBean));
        return true;
    }

    @Override
    public Boolean add(String name, String nickyName, String idCard, Integer gender, String phone, String email, String password) {
        UmsCustomer customer = new UmsCustomer(
                name,
                nickyName,
                idCard,
                gender,
                phone,
                email,
                "",
                passwordEncoder.encode(password)
        );
        return this.save(customer);
    }

    @Override
    public String login(String username, String code) {
        UmsCustomer customer = this.get(username);
        if (!redisTemplate.hasKey(customer.getEmail())){
            throw new CxlException("验证码超时");
        }
        Object o = redisTemplate.opsForValue().get(customer.getEmail());
        if (!code.equals(o.toString())){
            throw new CxlException("验证码错误");
        }
        redisTemplate.delete(customer.getEmail());
        return Entityutil.JwtEncoder(customer);
    }
}
