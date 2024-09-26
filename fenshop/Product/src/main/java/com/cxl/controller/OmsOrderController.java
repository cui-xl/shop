package com.cxl.controller;

import com.alipay.api.AlipayApiException;
import com.cxl.core.ResultJson;
import com.cxl.entity.OmsOrder;
import com.cxl.service.AlipayService;
import com.cxl.service.OmsOrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author cxl
 * @since 2023-12-11
 */
@RestController
@RequestMapping("/omsOrder")
public class OmsOrderController {
    @Resource
    OmsOrderService omsOrderService;
    @Resource
    AlipayService alipayService;

    @PostMapping("/add")
    ResultJson<String> add(BigDecimal total, Long open_id, String items) throws AlipayApiException {
        System.out.println(total);
        System.out.println(open_id);
        System.out.println(items);
//        return ResultJson.success("下单成功",alipayService.pay("3424222234",new BigDecimal("211")));
        return ResultJson.success("下单成功",omsOrderService.add(total, open_id, items));
    }
    @PostMapping("/updateStatus")
    ResultJson updateStatus(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        return ResultJson.success(omsOrderService.updateStatus(request));
    }
    @GetMapping("/list")
    ResultJson<List> list(Long open_id){
        return  ResultJson.success(omsOrderService.list(open_id));
    }
    @PostMapping("/pay")
    ResultJson<String> pay(String orderNo,BigDecimal total) throws AlipayApiException {
        return ResultJson.success(alipayService.pay(orderNo, total));
    }
}
