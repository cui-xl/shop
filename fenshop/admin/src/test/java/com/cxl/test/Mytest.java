package com.cxl.test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cxl.AdminApp;
import com.cxl.entity.UmsAdmin;
import com.cxl.service.UmsAdminService;
import com.cxl.util.Entityutil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApp.class)

public class Mytest {
    public static void main(String[] args) {
//        String sign = JWT.create().withClaim("name", "zhangsan")
//                .withClaim("id", 123)
//                .sign(Algorithm.HMAC256("cxl"));
//        System.out.println(sign);
        DecodedJWT cxl = JWT.require(Algorithm.HMAC256("cxl")).build().verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiemhhbmdzYW4iLCJpZCI6MTIzfQ.1ACkg_kzQRQSdQS4HPleww9gaCC0Znhxjc-G1z4cJ88");
        String string = cxl.getClaim("name").asString();
        Integer id = cxl.getClaim("id").asInt();
        System.out.println(string);
        System.out.println(id);


    }

}
