package com.cxl.service;

import com.cxl.entity.PmsSkuStock;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * SKU库存表  服务类
 * </p>
 *
 * @author cxl
 * @since 2023-11-03
 */
public interface PmsSkuStockService extends IService<PmsSkuStock> {
    List<PmsSkuStock> get(String productUid);
}
