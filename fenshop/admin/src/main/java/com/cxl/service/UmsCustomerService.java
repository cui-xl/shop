package com.cxl.service;

import com.cxl.entity.UmsCustomer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 客户表 服务类
 * </p>
 *
 * @author cxl
 * @since 2023-12-07
 */
public interface UmsCustomerService extends IService<UmsCustomer> {
    UmsCustomer get(String username);
    Boolean sendCode(String username);
    Boolean add(String name,String nickyName,String idCard,Integer gender,String phone,String email,String password);
    String login(String username,String code);
}
