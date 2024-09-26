package com.cxl.service;

import com.cxl.entity.OmsOrderItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单明细表 服务类
 * </p>
 *
 * @author cxl
 * @since 2023-12-11
 */
public interface OmsOrderItemService extends IService<OmsOrderItem> {
    List<OmsOrderItem> list(Long orderId);
}
