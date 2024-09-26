package com.cxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxl.entity.OmsOrderItem;
import com.cxl.mapper.OmsOrderItemMapper;
import com.cxl.service.OmsOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author cxl
 * @since 2023-12-11
 */
@Service
public class OmsOrderItemServiceImpl extends ServiceImpl<OmsOrderItemMapper, OmsOrderItem> implements OmsOrderItemService {

    @Override
    public List<OmsOrderItem> list(Long orderId) {
        QueryWrapper<OmsOrderItem> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id",orderId);
        return this.list(wrapper);
    }
}
