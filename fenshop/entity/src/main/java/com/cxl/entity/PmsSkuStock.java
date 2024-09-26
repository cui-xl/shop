package com.cxl.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cxl.core.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * SKU库存表 
 * </p>
 *
 * @author cxl
 * @since 2023-11-03
 */
@Getter
@Setter
@TableName("pms_sku_stock")
public class PmsSkuStock extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * sku组合
     */
    private String items;

    /**
     * 售价
     */
    private BigDecimal price;

    /**
     * 库存数
     */
    private Integer stock;

    /**
     * 商品uid
     */
    private String productUid;


}
