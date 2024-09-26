package com.cxl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cxl.core.ResultJson;
import com.cxl.service.PmsBrandService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author cxl
 * @since 2023-10-19
 */
@RestController
@RequestMapping("/pmsBrand")
public class PmsBrandController {
    @Resource
    PmsBrandService service;
    @GetMapping("/list")
    ResultJson list(Integer pageNo,Integer pageSize,String searchVal,Boolean active){
        return ResultJson.success(service.List(pageNo,pageSize,searchVal,active));
    }
    @PostMapping("/add")
    ResultJson add(String name, MultipartFile file, String description){
        return ResultJson.success("添加成功",service.add(name, file, description));
    }
    @PostMapping("update")
    ResultJson update(Long id, String name, MultipartFile file, String description){
        return ResultJson.success("修改成功",service.update(id,name,file,description));
    }
    @PostMapping("/del")
    ResultJson del(Long id,Boolean active){
        return  ResultJson.success(active?"启用成功":"禁用成功",service.del(id,active));
    }
    @GetMapping("/getById")
    ResultJson getById(Long id){

        return ResultJson.success(service.getById(id));
    }
    @GetMapping("/getAll")
    ResultJson<List> getAll(){
        return ResultJson.success(service.getAll());
    }


}
