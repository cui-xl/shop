package com.cxl.controller;

import com.cxl.core.ResultJson;
import com.cxl.service.UmsCustomerService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.xml.transform.Result;

/**
 * <p>
 * 客户表 前端控制器
 * </p>
 *
 * @author cxl
 * @since 2023-12-07
 */
@RestController
@RequestMapping("/umsCustomer")
public class UmsCustomerController {
    @Resource
    UmsCustomerService umsCustomerService;
    @GetMapping("/sendCode")
    ResultJson<Boolean> sendCode(String username){
        return ResultJson.success(umsCustomerService.sendCode(username));
    }
    @PostMapping("/add")
    ResultJson<Boolean> add(String name,String nickyName,String idCard,Integer gender,String phone,String email,String password){
        return ResultJson.success(umsCustomerService.add(name, nickyName, idCard, gender, phone, email, password));
    }
    @PostMapping("/login")
    ResultJson<String> login(String username,String code){
        return ResultJson.success(umsCustomerService.login(username,code));
    }
}
