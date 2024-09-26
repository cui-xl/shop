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
 * 商品表
 * </p>
 *
 * @author cxl
 * @since 2023-10-19
 */
@Getter
@Setter
@TableName("pms_brand")
@AllArgsConstructor
public class PmsBrand extends BaseEntity {
    public PmsBrand(Long id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }

    public PmsBrand() {
    }

    public PmsBrand(Long id, Boolean active) {
        super(id);
        this.active = active;
    }

    public PmsBrand(String name, String logo, String description, String uid) {
        this.name = name;
        this.logo = logo;
        this.description = description;
        this.uid = uid;
    }

    private static final long serialVersionUID = 1L;

    /**
     * 品牌名称
     */
    private String name;

    /**
     * 品牌Logo
     */
    private String logo;

    /**
     * 品牌说明
     */
    private String description;

    /**
     * 状态
     */
    private Boolean active;

    private String uid;


}
