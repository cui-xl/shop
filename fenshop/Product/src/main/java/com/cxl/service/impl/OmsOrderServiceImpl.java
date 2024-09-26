package com.cxl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cxl.core.CxlException;
import com.cxl.entity.OmsOrder;
import com.cxl.entity.OmsOrderItem;
import com.cxl.mapper.OmsOrderMapper;
import com.cxl.service.AlipayService;
import com.cxl.service.OmsCartService;
import com.cxl.service.OmsOrderItemService;
import com.cxl.service.OmsOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author cxl
 * @since 2023-12-11
 */
@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements OmsOrderService {
    @Resource
    OmsOrderItemService omsOrderItemService;
    @Resource
    OmsCartService omsCartService;
    @Resource
    AlipayService alipayService;
    @Override
    @Transactional
    public String add(BigDecimal total, Long open_id, String items) throws AlipayApiException {
        List<OmsOrderItem> list = JSONObject.parseArray(items, OmsOrderItem.class);
        //添加订单表
        StringBuilder orderNo=new StringBuilder();
        LocalDateTime now=LocalDateTime.now();
        orderNo.append(now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))).append(RandomStringUtils.random(15,false,true));
        OmsOrder omsOrder=new OmsOrder(
                orderNo.toString(),
                total,
                open_id,
                now
        );
        this.save(omsOrder);
        List<Long> ids = new ArrayList();
        //添加订单明细表
        for (OmsOrderItem omsOrderItem:list){
            ids.add(omsOrderItem.getId());
            omsOrderItem.setId(null);
            omsOrderItem.setOrderId(omsOrder.getId());
        }
        omsOrderItemService.saveBatch(list);
        //删除购物车表
        omsCartService.removeByIds(ids);
        System.out.println(list);
        return alipayService.pay(orderNo.toString(),total);
    }

    @Override
    public Boolean updateStatus(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        boolean flg=alipayService.check(request);
        if (flg){
            String OrderNo = request.getParameter("out_trade_no");
            UpdateWrapper<OmsOrder> wrapper= new UpdateWrapper();
            wrapper.eq("order_no",OrderNo);
            OmsOrder omsOrder = new OmsOrder();
            omsOrder.setStatus(1);
            return this.update(omsOrder,wrapper);
        }else{
            throw new CxlException("非法请求");
        }
    }

    @Override
    public List<OmsOrder> list(Long open_id) {
        QueryWrapper<OmsOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",open_id);
        List<OmsOrder> list = this.list(wrapper);
        for (OmsOrder omsOrder:list){
            List<OmsOrderItem> items = omsOrderItemService.list(omsOrder.getId());
            omsOrder.setItems(items);
        }
        return list;
    }
}
