package com.cxl.controller;

import com.cxl.core.ResultJson;
import com.cxl.entity.UmsResource;
import com.cxl.entity.UmsRole;
import com.cxl.entity.UmsRoleResource;
import com.cxl.service.UmsResourceService;
import com.cxl.service.UmsRoleResourceService;
import com.cxl.service.UmsRoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 角色关联权限表 前端控制器
 * </p>
 *
 * @author cxl
 * @since 2023-10-25
 */
@RestController
@RequestMapping("/umsRoleResource")
public class UmsRoleResourceController {
    @Resource
    UmsRoleService umsRoleService;
    @Resource
    UmsRoleResourceService umsRoleResourceService;
    @Resource
    UmsResourceService umsResourceService;
    @GetMapping("/getData")
    ResultJson<Map> getData(Long roleId){
         HashMap map = new HashMap();
        UmsRole umsRole = umsRoleService.getById(roleId);
        map.put("role",umsRole);
        map.put("resources",umsResourceService.getAll());
        map.put("resourceUids",umsRoleResourceService.getByRoleUid(umsRole.getUid()));
        return ResultJson.success(map);
    }
    @PostMapping("/save")
    ResultJson<Boolean> save(String roleUid,String[] front,String[] back){
        return ResultJson.success("保存成功",umsRoleResourceService.save(roleUid,front,back));
    }

}
