package com.cxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxl.entity.UmsResource;
import com.cxl.mapper.UmsResourceMapper;
import com.cxl.service.UmsResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxl.util.Entityutil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author cxl
 * @since 2023-10-23
 */
@Service
public class UmsResourceServiceImpl extends ServiceImpl<UmsResourceMapper, UmsResource> implements UmsResourceService {
    @Resource
    UmsResourceMapper umsResourceMapper;
    @Override
    @CacheEvict(value = "ums",key = "'resource'")
    public Boolean add(String name, Integer type, String frontUrl, String backUrl, String parentUid) {
        UmsResource resource = new UmsResource(name,type,frontUrl,backUrl,parentUid, Entityutil.uuid());
        return this.save(resource);
    }


    @Override
    @CacheEvict(value = "ums",key = "'resource'")
    public Boolean update(Long id, String name, Integer type, String frontUrl, String backUrl) {
        UmsResource resource = new UmsResource(id,name,type,frontUrl,backUrl);
        return this.updateById(resource);
    }

    @Resource(name = "rt")
    RedisTemplate<String,Object> redisTemplate;
    @Override
    @Cacheable(value = "ums",key = "'resource'")
    public List<UmsResource> getAll() {
            return this.getByParent("");
    }

    @Override
    public List<UmsResource> getByUser(String userUid) {
        return umsResourceMapper.getByUser(userUid);
    }

    private List<UmsResource> getByParent(String parentUid){
        QueryWrapper<UmsResource> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_uid",parentUid);
        List<UmsResource> list = this.list(wrapper);
        for (UmsResource umsr:list){
            List<UmsResource> children = this.getByParent(umsr.getUid());
            umsr.setChildren(children);

        }
        return list;
    }
}
