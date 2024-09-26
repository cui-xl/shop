package com.cxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cxl.entity.UmsRoleAdmin;
import com.cxl.mapper.UmsRoleAdminMapper;
import com.cxl.service.UmsRoleAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色用户关联表 服务实现类
 * </p>
 *
 * @author cxl
 * @since 2023-10-22
 */
@Service

public class UmsRoleAdminServiceImpl extends ServiceImpl<UmsRoleAdminMapper, UmsRoleAdmin> implements UmsRoleAdminService {

    @Override
    @Transactional
    public boolean save(String RoleUid, String[] adminUids) {
        UpdateWrapper wrapper=new UpdateWrapper();
        wrapper.eq("role_uid",RoleUid);
        this.remove(wrapper);
        List list=new ArrayList();
        for (String ad : adminUids){
            list.add( new UmsRoleAdmin(RoleUid,ad));
        }
        return this.saveBatch(list);
    }

    @Override
    public List<String> getAdminUids(String roleUid) {
        QueryWrapper<UmsRoleAdmin> wrapper = new QueryWrapper();
        System.out.println(roleUid);
        wrapper.eq("role_uid",roleUid);
        List<UmsRoleAdmin> list=this.list(wrapper);
        System.out.println(list);
        List<String> result = new ArrayList<>();
        if (list != null) {
            for (UmsRoleAdmin i : list) {
                result.add(i.getAdminUid());
            }
        }
        return result;
    }
}
