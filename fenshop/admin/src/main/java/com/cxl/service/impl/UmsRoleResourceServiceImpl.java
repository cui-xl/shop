package com.cxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cxl.entity.UmsRoleResource;
import com.cxl.mapper.UmsRoleResourceMapper;
import com.cxl.service.UmsRoleResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色关联权限表 服务实现类
 * </p>
 *
 * @author cxl
 * @since 2023-10-25
 */
@Service
public class UmsRoleResourceServiceImpl extends ServiceImpl<UmsRoleResourceMapper, UmsRoleResource> implements UmsRoleResourceService {

    @Override
    @Transactional
    public boolean save(String roleUid, String[] front, String[] back) {
        UpdateWrapper<UmsRoleResource> wrapper = new UpdateWrapper();
        wrapper.eq("role_uid",roleUid);
        this.remove(wrapper);
        List<UmsRoleResource> list=new ArrayList();
        if (front!=null){
            for (String resourceUid:front){
                list.add(new UmsRoleResource(roleUid,resourceUid,1));
            }
        }
        if (back!=null){
            for (String resourceUid:back){
                list.add(new UmsRoleResource(roleUid,resourceUid,0));
            }
        }
        return this.saveBatch(list);
    }

    @Override
    public List<String> getByRoleUid(String roleUid) {
        QueryWrapper<UmsRoleResource> wrapper = new QueryWrapper();
        wrapper.eq("role_uid",roleUid).eq("resource_type",0);
        List<UmsRoleResource> list = this.list(wrapper);
        List<String> resultList = new ArrayList();
        list.forEach(UmsRoleResource->{
            resultList.add(UmsRoleResource.getResourceUid());
        });
        return resultList;
    }
}
