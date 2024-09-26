package com.cxl.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cxl.core.BaseEntity;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author cxl
 * @since 2023-10-21
 */
@Getter
@Setter
@TableName("ums_role")
public class UmsRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    private String name;

    public UmsRole(Long id) {
        super(id);
    }

    public UmsRole() {
    }

    /**
     * 状态
     */
    private Boolean active;

    private String uid;


}
