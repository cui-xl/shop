package com.cxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxl.config.FileService;
import com.cxl.entity.PmsBrand;
import com.cxl.entity.UmsAdmin;
import com.cxl.mapper.PmsBrandMapper;
import com.cxl.service.PmsBrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxl.util.Entityutil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author cxl
 * @since 2023-10-19
 */
@Service
public class PmsBrandServiceImpl extends ServiceImpl<PmsBrandMapper, PmsBrand> implements PmsBrandService {
    @Resource
    FileService fileService;
    public IPage<PmsBrand> List(Integer pageNo,Integer pageSize,String searchVal,Boolean active) {
        QueryWrapper<PmsBrand> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(searchVal)){
            wrapper.like("name",searchVal);
        }
        if (active!=null){
            wrapper.eq("active",active);
        }
        return this.page(new Page<>(pageNo,pageSize),wrapper);
    }

    @Override
    @CacheEvict(value = "pms",key = "'brands'")
    public Boolean add(String name, MultipartFile Logo, String description) {
        PmsBrand pmsBrand = new PmsBrand(name, fileService.upload(Logo),description, Entityutil.uuid());
        return this.save(pmsBrand);
    }

    @Override
    @CacheEvict(value = "pms",key = "'brands'")
    public Boolean update(Long id, String name, MultipartFile Logo, String description) {
        PmsBrand pmsBrand = new PmsBrand(id, name, description);
        if (Logo!=null){
            pmsBrand.setLogo(fileService.upload(Logo));
        }
        return this.updateById(pmsBrand);
    }

    @Override
    @CacheEvict(value = "pms",key = "'brands'")
    public Boolean del(Long id, Boolean active) {
        PmsBrand pmsBrand = new PmsBrand(id, active);
        return this.updateById(pmsBrand);
    }

    @Override
    @Cacheable(value = "pms",key = "'brands'")
    public List<PmsBrand> getAll() {
        QueryWrapper<PmsBrand> queryWrapper = new QueryWrapper();
        queryWrapper.eq("active",1);
        return this.list(queryWrapper);
    }

    @Override
    public String getNameByUid(String uid) {
        QueryWrapper<PmsBrand> wrapper=new QueryWrapper();
        wrapper.eq("uid",uid);
        return this.getOne(wrapper).getName();
    }
}
