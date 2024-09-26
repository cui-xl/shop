package com.cxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxl.config.FileService;
import com.cxl.entity.PmsBrand;
import com.cxl.entity.PmsGood;
import com.cxl.entity.PmsSkuStock;
import com.cxl.mapper.PmsSkuStockMapper;
import com.cxl.service.PmsBrandService;
import com.cxl.service.PmsSkuStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * SKU库存表  服务实现类
 * </p>
 *
 * @author cxl
 * @since 2023-11-03
 */
@Service
public class PmsSkuStockServiceImpl extends ServiceImpl<PmsSkuStockMapper, PmsSkuStock> implements PmsSkuStockService {

    @Override
    public List<PmsSkuStock> get(String productUid) {
        QueryWrapper<PmsSkuStock> wrapper = new QueryWrapper<>();
        wrapper.eq("product_uid",productUid);
        List<PmsSkuStock> list = this.list(wrapper);
        return list;
    }
}
