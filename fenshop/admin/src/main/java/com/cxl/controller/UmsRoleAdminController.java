package com.cxl.controller;

import com.cxl.core.ResultJson;
import com.cxl.entity.UmsAdmin;
import com.cxl.entity.UmsRole;
import com.cxl.entity.UmsRoleAdmin;
import com.cxl.service.UmsAdminService;
import com.cxl.service.UmsRoleAdminService;
import com.cxl.service.UmsRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色用户关联表 前端控制器
 * </p>
 *
 * @author cxl
 * @since 2023-10-22
 */
@RestController
@RequestMapping("/umsRoleAdmin")
public class UmsRoleAdminController {
    @Resource
    UmsRoleService umsRoleService;
    @Resource
    UmsAdminService umsAdminService;
    @Resource
    UmsRoleAdminService umsRoleAdminService;
    @GetMapping("/getAdmin")
    ResultJson<List> getAdmin(){
        return  ResultJson.success(umsAdminService.getActive());
    }
    @PostMapping("/save")
    ResultJson<Boolean> save(String RoleUid,String[] adminUids){
        return  ResultJson.success("保存成功",umsRoleAdminService.save(RoleUid,adminUids));
    }
    @GetMapping("/AdminUid")
    ResultJson<List> getUid(String RoleUid){
        return ResultJson.success(umsRoleAdminService.getAdminUids(RoleUid));
    }
    @GetMapping("/getAllData")
    ResultJson<Map> getAllData(Long roleId){
        HashMap map= new HashMap();
        System.out.println(roleId);
        UmsRole umsRole = umsRoleService.getById(roleId);
        List<UmsAdmin> admins = umsAdminService.getActive();
        List<String> adminUids = umsRoleAdminService.getAdminUids(umsRole.getUid());
        map.put("umsRole",umsRole);
        map.put("admins",admins);
        map.put("adminUids",adminUids);
        return ResultJson.success(map);
    }
}
