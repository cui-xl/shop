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
 * 角色关联权限表
 * </p>
 *
 * @author cxl
 * @since 2023-10-25
 */
@Getter
@Setter
@TableName("ums_role_resource")
@NoArgsConstructor
public class UmsRoleResource extends BaseEntity {

    private static final long serialVersionUID = 1L;


    public UmsRoleResource(String roleUid, String resourceUid, Integer resourceType) {
        this.roleUid = roleUid;
        this.resourceUid = resourceUid;
        this.resourceType = resourceType;
    }

    /**
     * 角色的uid
     */
    private String roleUid;

    /**
     * 资源的uid
     */
    private String resourceUid;

    /**
     * 资源类别
     */
    private Integer resourceType;


}
