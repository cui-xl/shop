package com.cxl.controller;

import com.cxl.core.ResultJson;
import com.cxl.entity.PmsCategory;
import com.cxl.service.PmsCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author cxl
 * @since 2023-10-30
 */
@RestController
@RequestMapping("/pmsCategory")
public class PmsCategoryController {
    @Resource
    PmsCategoryService pmsCategoryService;
    @GetMapping("/list")
    ResultJson<List> list(){
        return ResultJson.success(pmsCategoryService.getAll());
    }
    @PostMapping("/add")
    ResultJson<Boolean> add(String name,String parentUid){
        return ResultJson.success("添加成功",pmsCategoryService.add(name,parentUid));

    }
    @PostMapping("/update")
    ResultJson<Boolean> update(Long id, String name){
        return ResultJson.success("修改成功",pmsCategoryService.update(id,name));
    }
    @GetMapping("/check")
    ResultJson<Boolean> check(String field,String value,Long id){
        return ResultJson.success(pmsCategoryService.check(field, value, id));
    }
    @GetMapping("/getById")
    ResultJson<PmsCategory> getById(Long id){
        return ResultJson.success(pmsCategoryService.getById(id));
    }
    @PostMapping("/del")
    ResultJson<Boolean> del(Long id,String uid){
        return ResultJson.success("删除分类成功",pmsCategoryService.del(id, uid));
    }
}
