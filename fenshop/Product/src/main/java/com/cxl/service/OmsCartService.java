package com.cxl.service;

import com.cxl.entity.OmsCart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 购物车 服务类
 * </p>
 *
 * @author cxl
 * @since 2023-12-07
 */
public interface OmsCartService extends IService<OmsCart> {
    Boolean add(Long open_id, Long productId, String productName, Long skuId, String skuName, BigDecimal price, Integer count, String src);
    List<OmsCart> list (Long open_id);
}
