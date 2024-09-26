package com.cxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxl.entity.UmsRole;
import com.cxl.mapper.UmsRoleMapper;
import com.cxl.service.UmsRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxl.util.Entityutil;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author cxl
 * @since 2023-10-21
 */
@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements UmsRoleService {

    @Override
    public IPage list(Integer pageNo, Integer pageSize, String searchVal, Boolean active) {
        QueryWrapper<UmsRole> wrapper = new QueryWrapper<>();
        if (searchVal!=null){
            wrapper.like("name",searchVal);
        }
        if (active!= null){
            wrapper.eq("active",active);
        }
        return this.page(new Page<>(pageNo,pageSize),wrapper);
    }

    @Override
    public Boolean add(String name) {
        UmsRole umsRole = new UmsRole();
        umsRole.setName(name);
        umsRole.setUid(Entityutil.uuid());
        return this.save(umsRole);
    }

    @Override
    public Boolean update(Long id, String name) {
        UmsRole umsRole = new UmsRole();
        umsRole.setId(id);
        umsRole.setName(name);
        return this.updateById(umsRole);
    }

    @Override
    public Boolean del(Long id, Boolean active) {
        UmsRole umsRole = new UmsRole(id);
        umsRole.setActive(active);
        return this.updateById(umsRole);
    }

    @Override
    public Boolean check(String name) {
        QueryWrapper<UmsRole> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        UmsRole umsRole = new UmsRole();
        umsRole.setName(name);

        return this.count(wrapper)==0;
    }
}
