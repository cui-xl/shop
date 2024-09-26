package com.cxl.service;

import com.cxl.entity.UmsRoleResource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色关联权限表 服务类
 * </p>
 *
 * @author cxl
 * @since 2023-10-25
 */
public interface UmsRoleResourceService extends IService<UmsRoleResource> {
boolean save(String roleUid,String[] front,String[] back);
List<String> getByRoleUid(String roleUid);
}
