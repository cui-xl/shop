package com.cxl.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxl.config.FileService;
import com.cxl.entity.PmsCategory;
import com.cxl.entity.PmsGood;
import com.cxl.entity.PmsSkuStock;
import com.cxl.mapper.PmsGoodMapper;
import com.cxl.service.PmsBrandService;
import com.cxl.service.PmsCategoryService;
import com.cxl.service.PmsGoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxl.service.PmsSkuStockService;
import com.cxl.util.Entityutil;
import com.sun.org.apache.xpath.internal.operations.Mult;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author cxl
 * @since 2023-11-01
 */
@Service
public class PmsGoodServiceImpl extends ServiceImpl<PmsGoodMapper, PmsGood> implements PmsGoodService {
    @Resource
    FileService fileService;
    @Resource
    PmsBrandService pmsBrandService;
    @Resource
    PmsCategoryService pmsCategoryService;
    @Resource
    PmsSkuStockService pmsSkuStockService;
    @Override
    @Transactional
    public boolean add(String name, String brandUid, BigDecimal price, MultipartFile[] files, String[] categoryUids, String spuAttr, String skuAttr, String stock, String description) throws IOException {
        String uid= Entityutil.uuid();
        PmsGood pmsGood = new PmsGood(name,
                pmsBrandService.getNameByUid(brandUid),
                price,
                spuAttr,
                skuAttr,
                description,
                uid
                );
        pmsGood.setCategoryName(pmsCategoryService.getNameByUids(categoryUids));
        ArrayList<String> album = new ArrayList<>();
        for (MultipartFile file:files){
            System.out.println(file.getName());

            MultipartFile newfile = new MockMultipartFile("file",file.getOriginalFilename(),file.getContentType(),file.getInputStream());
            album.add(fileService.upload(newfile));
        }
        pmsGood.setAlbum(JSONObject.toJSONString(album));
        List<PmsSkuStock> pmsSkuStocks = JSONObject.parseArray(stock, PmsSkuStock.class);
        for (PmsSkuStock p:pmsSkuStocks){
            p.setProductUid(uid);
        }
        pmsSkuStockService.saveBatch(pmsSkuStocks);
        return this.save(pmsGood);
    }

    @Override
    public IPage<PmsGood> list(Integer pageNo, Integer pageSize,String searchVal) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like("name",searchVal);
        return this.page(new Page<>(pageNo,pageSize),wrapper);
    }

    @Override
    public boolean changeStatus(Long id, String field, Boolean value) throws NoSuchFieldException, IllegalAccessException {
        PmsGood pmsGood = new PmsGood();
        pmsGood.setId(id);
        Class<PmsGood> aClass = PmsGood.class;
        Field declaredField = aClass.getDeclaredField(field);
        declaredField.setAccessible(true);
        declaredField.set(pmsGood,value);
        return this.updateById(pmsGood);
    }

    @Override
    public PmsGood get(Long id) {
        PmsGood good = this.getById(id);
        good.setSkuStockList(pmsSkuStockService.get(good.getUid()));
        return good;
    }
}
