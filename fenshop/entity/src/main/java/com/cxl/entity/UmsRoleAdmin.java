package com.cxl.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cxl.core.BaseEntity;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色用户关联表
 * </p>
 *
 * @author cxl
 * @since 2023-10-22
 */
@Getter
@Setter
@TableName("ums_role_admin")
public class UmsRoleAdmin extends BaseEntity {

    public UmsRoleAdmin(String roleUid) {
        this.roleUid = roleUid;
    }

    public UmsRoleAdmin(Long id, String roleUid, String adminUid) {
        super(id);
        this.roleUid = roleUid;
        this.adminUid = adminUid;
    }

    private static final long serialVersionUID = 1L;

    public UmsRoleAdmin(String roleUid, String adminUid) {
        this.roleUid = roleUid;
        this.adminUid = adminUid;
    }

    /**
     * 角色uid
     */
    private String roleUid;

    /**
     * 用户uid
     */
    private String adminUid;


}
