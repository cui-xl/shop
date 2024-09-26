package com.cxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxl.entity.OmsCart;
import com.cxl.mapper.OmsCartMapper;
import com.cxl.service.OmsCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 购物车 服务实现类
 * </p>
 *
 * @author cxl
 * @since 2023-12-07
 */
@Service
public class OmsCartServiceImpl extends ServiceImpl<OmsCartMapper, OmsCart> implements OmsCartService {

    @Override
    public Boolean add(Long open_id, Long productId, String productName, Long skuId, String skuName, BigDecimal price, Integer count, String src) {
        OmsCart omsCart = new OmsCart(
                open_id,
                productId,
                productName,
                skuId,
                skuName,
                price,
                count,
                src
        );
        return this.save(omsCart);
    }

    @Override
    public List<OmsCart> list(Long open_id) {
        QueryWrapper<OmsCart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",open_id);
        return this.list(wrapper);
    }
}
