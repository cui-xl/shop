package com.cxl.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cxl.core.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

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
 * @since 2023-11-01
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("pms_good")
public class PmsGood extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public PmsGood(String name, String brandName, BigDecimal price, String spuAttr, String skuAttr, String description,String uid) {
        this.name = name;
        this.brandName = brandName;
        this.price = price;
        this.spuAttr = spuAttr;
        this.skuAttr = skuAttr;
        this.description = description;
        this.uid=uid;
    }

    /**
     * 商品名称
     */
    private String name;

    /**
     * 避免表连接，归哪个品牌的名称
     */
    private String brandName;

    /**
     * 商品分类
     */
    private String categoryName;

    /**
     * 展示的价格
     */
    private BigDecimal price;

    /**
     * 商品相册
     */
    private String album;

    /**
     * spu属性
     */
    private String spuAttr;

    /**
     * sku属性
     */
    private String skuAttr;

    /**
     * 商品详情
     */
    private String description;

    /**
     * 是否新品
     */
    private Boolean isNew;

    /**
     * 是否热卖
     */
    private Boolean isHot;

    /**
     * 是否上架
     */
    private Boolean isPublish;

    /**
     * 状态
     */
    private Boolean active;

    private String uid;
    @TableField(exist = false)
    private List<PmsSkuStock> skuStockList;
}
