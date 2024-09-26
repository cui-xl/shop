package com.cxl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cxl.entity.PmsGood;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.lang.model.type.IntersectionType;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author cxl
 * @since 2023-11-01
 */
public interface PmsGoodService extends IService<PmsGood> {
    boolean add(String name, String brandUid, BigDecimal price, MultipartFile[] files, String[] categoryUids, String spuAttr, String skuAttr, String stock, String description) throws IOException;
    IPage<PmsGood> list(Integer pageNo,Integer pageSize,String searchVal);
    boolean changeStatus(Long id, String field, Boolean value) throws NoSuchFieldException, IllegalAccessException;
    PmsGood get(Long id);
}
