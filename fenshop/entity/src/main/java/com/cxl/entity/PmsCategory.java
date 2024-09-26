package com.cxl.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cxl.core.BaseEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * 分类表
 * </p>
 *
 * @author cxl
 * @since 2023-10-30
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("pms_category")
public class PmsCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public PmsCategory(Long id, String name) {
        super(id);
        this.name = name;
    }

    public PmsCategory(String name, String uid, String parentUid) {
        this.name = name;
        this.uid = uid;
        this.parentUid = parentUid;
    }

    /**
     * 分类名
     */
    private String name;

    /**
     * 分类uid
     */
    private String uid;

    /**
     * 上级uid
     */
    private String parentUid;
    /*
    子分类
     */
    @TableField(exist = false)
    private List<PmsCategory> Children;

}
