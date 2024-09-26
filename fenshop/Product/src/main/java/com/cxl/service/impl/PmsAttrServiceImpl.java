package com.cxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxl.entity.PmsAttr;
import com.cxl.entity.PmsCategory;
import com.cxl.mapper.PmsAttrMapper;
import com.cxl.service.PmsAttrService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxl.service.PmsCategoryService;
import com.cxl.util.Entityutil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 属性表 服务实现类
 * </p>
 *
 * @author cxl
 * @since 2023-10-30
 */
@Service
public class PmsAttrServiceImpl extends ServiceImpl<PmsAttrMapper, PmsAttr> implements PmsAttrService {
    @Override
    public boolean add(String name, Integer type, String categoryUid) {
        return this.save(new PmsAttr(categoryUid,name,type, Entityutil.uuid()));
    }

    @Override
    public List<PmsAttr> list(String[] categoryUids) {
        List<PmsAttr> list = new ArrayList<>();
        for (String categoryUid: categoryUids){
            list.addAll(this.getByCategory(categoryUid));
        }
        return list;
    }

    @Override
    public boolean update(Long id, String name, Integer type) {
        return this.updateById(new PmsAttr(id,name,type));
    }
    @Override
    public boolean del(Long id) {
        return this.removeById(new PmsAttr(id));
    }
    private List<PmsAttr> getByCategory(String categoryUid){  //根据分类属性单个查询
        QueryWrapper<PmsAttr> wrapper = new QueryWrapper<>();
        wrapper.eq("category_uid",categoryUid);
        return this.list(wrapper);
    }
}
