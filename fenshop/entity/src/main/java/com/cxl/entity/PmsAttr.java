package com.cxl.entity;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cxl.core.BaseEntity;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * 属性表
 * </p>
 *
 * @author cxl
 * @since 2023-10-30
 */
@Getter
@Setter
@TableName("pms_attr")
public class PmsAttr extends BaseEntity {
    public PmsAttr() {
    }

    public PmsAttr(Long id) {
        super(id);
    }

    public PmsAttr(Long id, String name, Integer type) {
        super(id);
        this.name = name;
        this.type = type;
    }

    private static final long serialVersionUID = 1L;

    /**
     * 分类uid
     */
    private String categoryUid;

    public PmsAttr(String categoryUid, String name, Integer type, String uid) {
        this.categoryUid = categoryUid;
        this.name = name;
        this.type = type;
        this.uid = uid;
    }

    public PmsAttr(Long id, String uid) {
        super(id);
        this.uid = uid;
    }

    /**
     * 属性名称
     */
    private String name;

    /**
     * 1-spu 0=sku
     */
    private Integer type;

    /**
     * uid
     */
    private String uid;


}
