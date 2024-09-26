package com.cxl.controller;

import com.cxl.core.ResultJson;
import com.cxl.service.OmsCartService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 购物车 前端控制器
 * </p>
 *
 * @author cxl
 * @since 2023-12-07
 */
@RestController
@RequestMapping("/omsCart")
public class OmsCartController {
    @Resource
    OmsCartService omsCartService;
    @GetMapping("/list")
    ResultJson<List> list(Long open_id){
        return ResultJson.success(omsCartService.list(open_id));
    };
    @PostMapping("/add")
    ResultJson<Boolean> add(Long open_id, Long productId, String productName, Long skuId, String skuName, BigDecimal price,Integer count,String src){
        return ResultJson.success("添加购购物车成功，请去购物车下单",omsCartService.add(open_id, productId, productName, skuId, skuName, price, count, src));
    }
}
