package com.cxl.service;

import com.alipay.api.AlipayApiException;
import com.cxl.entity.OmsOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author cxl
 * @since 2023-12-11
 */
public interface OmsOrderService extends IService<OmsOrder> {
    String add(BigDecimal total, Long open_id, String items) throws AlipayApiException;
    Boolean updateStatus(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException;
    List<OmsOrder> list(Long open_id);
}
