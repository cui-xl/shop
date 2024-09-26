package com.cxl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cxl.config.FileService;
import com.cxl.core.ResultJson;
import com.cxl.entity.PmsGood;
import com.cxl.service.PmsAttrService;
import com.cxl.service.PmsBrandService;
import com.cxl.service.PmsCategoryService;
import com.cxl.service.PmsGoodService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.xml.transform.Result;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author cxl
 * @since 2023-11-01
 */
@RestController
@RequestMapping("/pmsGood")
public class PmsGoodController {
    @Resource
    PmsBrandService pmsBrandService;
    @Resource
    FileService fileService;
    @Resource
    PmsAttrService pmsAttrService;
    @Resource
    PmsCategoryService pmsCategoryService;
    @Resource
    PmsGoodService pmsGoodService;
    @GetMapping("/getData")
    ResultJson<Map>  getData(){
        Map<String , List> map =new HashMap<>();
        map.put("brands",pmsBrandService.getAll());
        map.put("categories",pmsCategoryService.getAll());
        return ResultJson.success(map);
    }
    @GetMapping("/getAttr")
    ResultJson<List> getAttr(String[] categoryUid){
        return ResultJson.success(pmsAttrService.list(categoryUid));
    }
    @PostMapping("/upload")
    ResultJson<String> upload(MultipartFile file){
        return  ResultJson.success(fileService.upload(file));
    }
    @PostMapping("/add")
    ResultJson<Boolean> add(String name, String brandUid, BigDecimal price,MultipartFile[] files,String[] categoryUids,String spuAttr,String skuAttr,String stock,String description) throws IOException {
        System.out.println(skuAttr);
        System.out.println(spuAttr);
        return ResultJson.success("添加成功",pmsGoodService.add(name, brandUid, price, files, categoryUids, spuAttr, skuAttr, stock, description));
    }
    @GetMapping("/list")
    ResultJson<IPage> list(Integer pageNo,Integer pageSize,String searchVal){
        return ResultJson.success(pmsGoodService.list(pageNo,pageSize,searchVal));
    }
    @PostMapping("changeStatus")
     ResultJson<Boolean> changeStatus(Long id, String field, Boolean value) throws NoSuchFieldException, IllegalAccessException {
        return ResultJson.success("更新"+field+"成功",pmsGoodService.changeStatus(id, field, value));
    }
    @GetMapping("getById")
    ResultJson<PmsGood> getById(Long id){
        return  ResultJson.success(pmsGoodService.get(id));
    }
}
