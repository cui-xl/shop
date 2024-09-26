package com.cxl.controller;

import com.cxl.core.ResultJson;
import com.cxl.entity.PmsAttr;
import com.cxl.service.PmsAttrService;
import com.cxl.service.PmsCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.xml.transform.Result;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 属性表 前端控制器
 * </p>
 *
 * @author cxl
 * @since 2023-10-30
 */
@RestController
@RequestMapping("/pmsAttr")
public class PmsAttrController {
    @Resource
    PmsCategoryService pmsCategoryService;
    @Resource
    PmsAttrService pmsAttrService;
    @GetMapping("getCategories")   //属性和分类有关
    ResultJson<List> getCategories(){
        return  ResultJson.success(pmsCategoryService.getAll());
    }
    @PostMapping("/add")
    ResultJson<Boolean> add(String name,Integer type,String categoryUid){
        return ResultJson.success("添加成功",pmsAttrService.add(name, type, categoryUid));
    }
    @GetMapping("/list")
    ResultJson<List> list(String[] categoryUids){
        System.out.println(Arrays.toString(categoryUids));
        return ResultJson.success(pmsAttrService.list(categoryUids));
    }
    @GetMapping("/getById")
    ResultJson<PmsAttr> getById(Long id){
        return ResultJson.success(pmsAttrService.getById(id));
    }
    @PostMapping("/del")
    ResultJson<Boolean> del(Long id){
        return ResultJson.success("删除成功",pmsAttrService.del(id));
    }
    @PostMapping("/update")
    ResultJson<Boolean> update(Long id,String name,Integer type){
        return ResultJson.success("修改成功",pmsAttrService.update(id,name,type));
    }
}
