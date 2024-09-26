package com.cxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxl.core.CxlException;
import com.cxl.entity.PmsCategory;
import com.cxl.mapper.PmsCategoryMapper;
import com.cxl.service.PmsCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxl.util.Entityutil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author cxl
 * @since 2023-10-30
 */
@Service
public class PmsCategoryServiceImpl extends ServiceImpl<PmsCategoryMapper, PmsCategory> implements PmsCategoryService {

    @Override
    @Cacheable(value = "pms",key = "'categories'")
    public List<PmsCategory> getAll() {
        return this.getByParentUid("");
    }
    //层级关系来递归
    private List<PmsCategory> getByParentUid(String parentUid){
        QueryWrapper<PmsCategory> wrapper = new QueryWrapper();
        wrapper.eq("parent_uid",parentUid);
        List<PmsCategory> list = this.list(wrapper);
        for (PmsCategory pmsCategory:list){
            List<PmsCategory> children = this.getByParentUid(pmsCategory.getUid());
            if(children.size()>0){
                pmsCategory.setChildren(children);
            }
        }
        return list;
    }
    @Override
    @CacheEvict(value = "pms",key = "'categories'")
    public boolean add(String name,String parentUid) {
        return this.save(new PmsCategory(name, Entityutil.uuid(),parentUid));
    }

    @Override
    @CacheEvict(value = "pms",key = "'categories'")
    public boolean update(Long id, String name) {
        return this.updateById(new PmsCategory(id,name));
    }

    @Override
    @CacheEvict(value = "pms",key = "'categories'")
    public boolean del(Long id, String uid) {
        QueryWrapper<PmsCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_uid",uid);
        if (this.count(wrapper)>0){
            throw new CxlException("存在未删除的下级，无法删除");
        }
        return this.removeById(id);
    }
    @Override
    public boolean check(String field, String value, Long id) {
        QueryWrapper<PmsCategory> wrapper = new QueryWrapper<>();
        wrapper.eq(field,value);   //查这个表中所有行的字段与value匹配的值
        if (null!=id){    //即 是修改而不是增加
             wrapper.ne("id",id);   //把不是我这个id的行拿出来
        }
        return this.count(wrapper)==0;  //我这个id的行查出的value值为零行，就说明检查通过，没有相同的值
    }

    @Override
    public String getNameByUids(String[] uids) {
        List<String> name=new ArrayList<>();
        for (String uid :uids){
            name.add(this.getNameByUid(uid));
        }
        return String.join("/",name);
    }
    private String getNameByUid(String uid){
        QueryWrapper<PmsCategory> wrapper=new QueryWrapper();
        wrapper.eq("uid",uid);
        return this.getOne(wrapper).getName();
    }
}
