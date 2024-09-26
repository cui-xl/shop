package com.cxl.controller;

import com.cxl.core.ResultJson;
import com.cxl.entity.UmsResource;
import com.cxl.service.UmsResourceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author cxl
 * @since 2023-10-23
 */
@RestController
@RequestMapping("/umsResource")
public class UmsResourceController {

    @Resource
    UmsResourceService umsResourceService;
    @GetMapping("/list")
    public ResultJson<List> list(String searchVal){
        return ResultJson.success(umsResourceService.getAll());
    }
    @PostMapping("/add")
    public ResultJson<Boolean> add(String name,Integer type,String frontUrl,String backUrl,String parentUid){

        return ResultJson.success("添加成功",umsResourceService.add(name, type, frontUrl, backUrl, parentUid));
    }
    @PostMapping("/update")
    public ResultJson<Boolean> update(Long id,String name,Integer type,String frontUrl,String backUrl){
        return  ResultJson.success("修改成功",umsResourceService.update(id,name,type,frontUrl,backUrl));
    }
    @GetMapping("/getById")
    public ResultJson<UmsResource> getById(Long id){
        return  ResultJson.success(umsResourceService.getById(id));
    }
}
