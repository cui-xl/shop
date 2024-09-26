package com.cxl.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cxl.entity.UmsAdmin;
import com.cxl.entity.UmsCustomer;

import java.util.UUID;

public class Entityutil {
    private final static String KEY="CUIXINLEI";
    public static  String uuid(){

        return UUID.randomUUID().toString().replaceAll("\\-","");
    }
    public static String JwtEncoder(UmsAdmin umsAdmin){
        return JWT.create().withClaim("id",umsAdmin.getId())
                .withClaim("name",umsAdmin.getName())
                .withClaim("uid",umsAdmin.getUid())
                .sign(Algorithm.HMAC256(KEY));
    }
    public static String JwtEncoder(UmsCustomer  umsCustomer){
        return JWT.create().withClaim("id",umsCustomer.getId())
                .withClaim("name",umsCustomer.getName())
                .withClaim("nickyName",umsCustomer.getNickyName())
                .sign(Algorithm.HMAC256(KEY));
    }
    public static UmsAdmin JwtDecoder(String token){
        UmsAdmin umsAdmin = new UmsAdmin();
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(KEY)).build().verify(token);
        Long id = jwt.getClaim("id").asLong();
        String name = jwt.getClaim("name").asString();
        String uid = jwt.getClaim("uid").asString();
        umsAdmin.setId(id);
        umsAdmin.setUid(uid);
        umsAdmin.setName(name);
        return umsAdmin;
    }
    public static UmsCustomer JwtCustomerDecoder(String token){
        UmsCustomer customer = new UmsCustomer();
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(KEY)).build().verify(token);
        Long id = jwt.getClaim("id").asLong();
        String name = jwt.getClaim("name").asString();
        String nickyName = jwt.getClaim("nickyName").asString();
        customer.setId(id);
        customer.setNickyName(nickyName);
        customer.setName(name);
        return customer;
    }
}
