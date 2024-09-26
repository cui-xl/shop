package com.cxl.service;

import com.cxl.entity.PmsAttr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 属性表 服务类
 * </p>
 *
 * @author cxl
 * @since 2023-10-30
 */
public interface PmsAttrService extends IService<PmsAttr> {
boolean add (String name,Integer type,String categoryUid); //根据哪一类，添加它的属性
List<PmsAttr> list(String[] categoryUids);
boolean update(Long id,String name,Integer type);
boolean del(Long id);
}
