package com.cxl.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cxl.core.BaseEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author cxl
 * @since 2023-10-23
 */
@Getter
@Setter
@TableName("ums_resource")
@AllArgsConstructor
@NoArgsConstructor
public class UmsResource extends BaseEntity {
    private static final long serialVersionUID = 1L;
    public UmsResource(String name, Integer type, String frontUrl, String backUrl, String parentUid,String uid) {
        this.name = name;
        this.type = type;
        this.frontUrl = frontUrl;
        this.backUrl = backUrl;
        this.parentUid = parentUid;
        this.uid=uid;
    }
    public UmsResource(Long id, String name, Integer type, String frontUrl, String backUrl, String parentUid, String uid) {
        super(id);
        this.name = name;
        this.type = type;
        this.frontUrl = frontUrl;
        this.backUrl = backUrl;
        this.parentUid = parentUid;
        this.uid = uid;
    }
    public UmsResource(Long id, String name, Integer type, String frontUrl, String backUrl) {
        super(id);
        this.name = name;
        this.type = type;
        this.frontUrl = frontUrl;
        this.backUrl = backUrl;
    }


    /**
     * 资源名称
     */
    private String name;

    /**
     * 1-目录 0-按钮
     */
    private Integer type;

    /**
     * 前端地址
     */
    private String frontUrl;

    /**
     * 后端地址
     */
    private String backUrl;

    /**
     * uid
     */
    private String uid;

    /**
     * 上级uid
     */
    private String parentUid;
    @TableField(exist = false)
    private List<UmsResource> children =new ArrayList<>();

    @Override
    public String toString() {
        return "UmsResource{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", frontUrl='" + frontUrl + '\'' +
                ", backUrl='" + backUrl + '\'' +
                ", uid='" + uid + '\'' +
                ", parentUid='" + parentUid + '\'' +
                ", children=" + children +
                '}';
    }
}
