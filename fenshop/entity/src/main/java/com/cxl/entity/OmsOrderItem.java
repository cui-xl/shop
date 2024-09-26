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
 * 订单明细表
 * </p>
 *
 * @author cxl
 * @since 2023-12-11
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("oms_order_item")
public class OmsOrderItem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 订单表id
     */
    private Long orderId;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * sku编号
     */
    private Long skuId;

    /**
     * sku名称
     */
    private String skuName;

    /**
     * 图片
     */
    private String src;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private Integer count;


}
