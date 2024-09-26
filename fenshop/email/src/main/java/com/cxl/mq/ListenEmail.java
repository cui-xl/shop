package com.cxl.mq;

import com.alibaba.fastjson.JSONObject;
import com.cxl.email.EmailBean;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class ListenEmail {
    @Value("${spring.mail.username}")
    String from;
    @Resource
    JavaMailSender javaMailSender;
    @RabbitListener(queues = "email")
    public void handler(Message mes) {
        try{
            String s = new String(mes.getBody());
            EmailBean emailBean = JSONObject.parseObject(s, EmailBean.class);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setTo(emailBean.getTo());
            helper.setText(emailBean.getText(),true);
            helper.setSubject(emailBean.getObject());
            helper.setFrom(from);
            javaMailSender.send(mimeMessage);
        }catch (Exception ex){
            ex.printStackTrace();

        }





    }

}
