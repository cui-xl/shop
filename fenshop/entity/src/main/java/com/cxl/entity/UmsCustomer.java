package com.cxl.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cxl.core.BaseEntity;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * 客户表
 * </p>
 *
 * @author cxl
 * @since 2023-12-07
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("ums_customer")
public class UmsCustomer extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public UmsCustomer(String name, String nickyName, String idCard, Integer gender, String phone, String email, String icon, String password) {
        this.name = name;
        this.nickyName = nickyName;
        this.idCard = idCard;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.icon = icon;
        this.password = password;
    }

    public UmsCustomer(String name, String nickyName, String idCard, Integer gender, String phone, String email, String password) {
        this.name = name;
        this.nickyName = nickyName;
        this.idCard = idCard;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 昵称
     */
    private String nickyName;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 1-男，0-女
     */
    private Integer gender;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 状态
     */
    private Boolean active;

    /**
     * 头像
     */
    private String icon;

    /**
     * 登陆密码
     */
    private String password;


}
