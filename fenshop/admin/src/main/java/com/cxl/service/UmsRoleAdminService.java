package com.cxl.service;

import com.cxl.entity.UmsRoleAdmin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色用户关联表 服务类
 * </p>
 *
 * @author cxl
 * @since 2023-10-22
 */
public interface UmsRoleAdminService extends IService<UmsRoleAdmin> {
boolean save(String roleUid,String[] adminUids);
List<String> getAdminUids(String RoleUid);

}
