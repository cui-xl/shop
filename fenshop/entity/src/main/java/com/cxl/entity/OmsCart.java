package com.cxl.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cxl.core.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * 购物车
 * </p>
 *
 * @author cxl
 * @since 2023-12-07
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("oms_cart")
public class OmsCart extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    public OmsCart(Long userId, Long productId, String productName, Long skuId, String skuName, BigDecimal price, Integer count, String src) {
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.skuId = skuId;
        this.skuName = skuName;
        this.price = price;
        this.count = count;
        this.src = src;
    }

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * skuid
     */
    private Long skuId;

    /**
     * sku组合
     */
    private String skuName;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 商品图片
     */
    private String src;


}
