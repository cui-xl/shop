package com.cxl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cxl.entity.UmsRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author cxl
 * @since 2023-10-21
 */
public interface UmsRoleService extends IService<UmsRole> {
    IPage list(Integer pageNo,Integer pageSize,String searchVal,Boolean active);
    Boolean add(String name);
    Boolean update(Long id, String name);
    Boolean del(Long id,Boolean active);
    Boolean check(String name);
}
