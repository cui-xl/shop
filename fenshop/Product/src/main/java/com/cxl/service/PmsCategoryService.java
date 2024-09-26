package com.cxl.service;

import com.cxl.entity.PmsCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author cxl
 * @since 2023-10-30
 */
public interface PmsCategoryService extends IService<PmsCategory> {
    List<PmsCategory> getAll();
    boolean add(String name,String parentUid);
    boolean update (Long id, String name);
    boolean del(Long id,String uid);
    boolean check (String field,String value,Long id);
    String getNameByUids(String[] uids);
}
