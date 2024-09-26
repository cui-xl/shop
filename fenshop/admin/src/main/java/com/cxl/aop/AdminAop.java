package com.cxl.aop;

import com.alibaba.fastjson.JSONObject;
import com.cxl.core.ResultJson;
import com.cxl.email.EmailBean;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.amqp.rabbit.connection.RabbitAccessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@SuppressWarnings("ALL")
@Component
@Aspect
public class AdminAop {
//    @Around(value="execution(* com.cxl.controller.*.*(..))")
//    public Object sendMessage(ProceedingJoinPoint joinPoint){
//        try {
//           return joinPoint.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//            return ResultJson.failed("失败了了");
//        }
//
//    }

    @Resource
    RabbitTemplate rabbitTemplate;
    @AfterReturning(value = "execution( public * com.cxl.service.impl.UmsAdminServiceImpl.add(..))")
    public void sendEmail(JoinPoint joinPoint){
        //joinPoint获取切点，即哪个方法
        Object[] args=joinPoint.getArgs();    //获取方法的参数类型和值  注意是Object类型
        String email=String.valueOf(args[6]);   //将Object转成字符串
        String name=String.valueOf(args[0]);
        String password=String.valueOf(args[1]);
        String phone=String.valueOf(args[5]);
        //定义内容
        String suject= "系统消息";
        StringBuilder builder= new StringBuilder();
        builder.append("<p>"+name+"你好,</p>")
                .append("<div>您的登陆名是:"+name+""+"密码是:"+password+"</div>");
        EmailBean emailBean = new EmailBean(email,suject,builder.toString());
        rabbitTemplate.convertAndSend("cxl.exchange","emailkey", JSONObject.toJSONString(emailBean));
    }


}
