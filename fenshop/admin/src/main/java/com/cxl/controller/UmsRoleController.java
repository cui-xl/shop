package com.cxl.controller;

import com.cxl.core.ResultJson;
import com.cxl.service.UmsRoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author cxl
 * @since 2023-10-21
 */
@RestController
@RequestMapping("/umsRole")
public class UmsRoleController {
    @Resource
    UmsRoleService umsRoleService;

    @GetMapping("/list")
    ResultJson List(Integer pageNo,Integer pageSize,String searchVal,Boolean active){
        return ResultJson.success(umsRoleService.list(pageNo,pageSize,searchVal,active)) ;
    }
    @PostMapping("/add")
    ResultJson add(String name){
        return ResultJson.success("添加成功",umsRoleService.add(name));
    }
    @PostMapping("/update")
    ResultJson update(Long id,String name){
        return ResultJson.success("修改成功",umsRoleService.update(id,name));
    }
    @PostMapping("del")
    ResultJson del(Long id,Boolean active){
        return ResultJson.success(active?"启用成功":"禁用成功",umsRoleService.del(id,active));
    }
    @GetMapping("/getById")
    ResultJson getById(Long id){
        return ResultJson.success(umsRoleService.getById(id));
    }
    @GetMapping("/check")
    ResultJson check(String name){
        return ResultJson.success(umsRoleService.check(name));
    }


}
