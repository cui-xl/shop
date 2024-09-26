package com.cxl.service;

import com.cxl.entity.UmsResource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author cxl
 * @since 2023-10-23
 */
public interface UmsResourceService extends IService<UmsResource> {
Boolean add(String name,Integer type,String frontUrl,String backUrl,String parentUid);
Boolean update(Long id,String name,Integer type,String frontUrl,String backUrl);
List<UmsResource> getAll();
List<UmsResource> getByUser(String userUid);
}
