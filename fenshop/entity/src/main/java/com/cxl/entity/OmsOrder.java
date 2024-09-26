package com.cxl.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cxl.core.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author cxl
 * @since 2023-12-11
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("oms_order")
public class OmsOrder extends BaseEntity {
    public OmsOrder(String orderNo, BigDecimal total, Long userId, LocalDateTime createDate) {
        this.orderNo = orderNo;
        this.total = total;
        this.userId = userId;
        this.createDate = createDate;
    }

    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单总价
     */
    private BigDecimal total;

    /**
     * 哪个用户下的订单
     */
    private Long userId;

    /**
     * 0-未付款，1-已付款
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 是否删除
     */
    private Boolean active;

    @TableField(exist = false)
    private List<OmsOrderItem> items;

}
