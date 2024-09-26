package com.cxl.controller;

import com.cxl.core.ResultJson;
import com.cxl.entity.UmsAdmin;
import com.cxl.service.UmsAdminService;
import com.cxl.util.Entityutil;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author cxl
 * @since 2023-10-10
 */
@RestController
@RequestMapping("/umsAdmin")
public class UmsAdminController {
    @Resource
    UmsAdminService umsAdminService;

    @SneakyThrows
    @GetMapping("/list")
    public ResultJson list(String searchVal, Integer pageNo, Integer pageSize, Boolean active, HttpServletRequest request) {
            TimeUnit.SECONDS.sleep(1);
//            System.out.println("调用list");

            return ResultJson.success(umsAdminService.list(searchVal,pageNo,pageSize,active));
//        return umsAdminService.page(new Page<>(pageNo,pageSize));

    }
    @PostMapping("/add")
    public ResultJson add(String name, String password, String gender, LocalDate birthday, MultipartFile file, String phone, String email) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        return ResultJson.success("添加成功",umsAdminService.add(name, password, gender, birthday,file, phone, email));
    }
    @GetMapping("/getById")
    ResultJson<UmsAdmin> getById(Long id){
        return ResultJson.success(umsAdminService.getById(id));
    }
    @PostMapping("/update")
    ResultJson<Boolean> update(Long id,String name, String gender, LocalDate birthday, MultipartFile file, String phone, String email){
        return ResultJson.success("修改成功",umsAdminService.update(id,name,gender,birthday,file,phone,email));
    }
    @PostMapping("/del")
    ResultJson<Boolean> delete(Long id, Boolean active){
        return ResultJson.success(active?"启用成功":"禁用成功",umsAdminService.delete(id,active));
    }
    @PostMapping("/login")
    ResultJson<Map> login(String username, String password){
        return ResultJson.success(umsAdminService.login(username,password));
    }

}
