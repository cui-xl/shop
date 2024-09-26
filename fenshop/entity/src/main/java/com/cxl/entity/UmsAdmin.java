package com.cxl.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cxl.core.BaseEntity;
import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author cxl
 * @since 2023-10-10
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("ums_admin")
public class UmsAdmin extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员姓名
     */
    private String name;

    /**
     * 登录密码
     */
    @JsonIgnore
    private String password;

    /**
     * 性别
     */
    private String gender;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 头像
     */
    private String icon;

    /**
     * 状态
     */
    private Boolean active;

    /**
     * uid
     */
    private String uid;
    private String phone;
    private  String email;

    public UmsAdmin(String name, String password, String gender, LocalDate birthday, String icon,String uid, String phone, String email) {
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.icon = icon;
        this.phone = phone;
        this.email = email;
        this.uid=uid;
    }

    public UmsAdmin(String name, String gender, LocalDate birthday, String phone, String email) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String toString() {
        return "UmsAdmin{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", icon='" + icon + '\'' +
                ", active=" + active +
                ", uid='" + uid + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
