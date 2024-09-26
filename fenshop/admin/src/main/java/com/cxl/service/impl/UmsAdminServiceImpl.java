package com.cxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxl.api.FileService;
import com.cxl.core.ResultJson;
import com.cxl.entity.UmsAdmin;
import com.cxl.entity.UmsResource;
import com.cxl.exception.CxlException;
import com.cxl.mapper.UmsAdminMapper;
import com.cxl.service.UmsAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxl.service.UmsResourceService;
import com.cxl.util.Entityutil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author cxl
 * @since 2023-10-10
 */
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements UmsAdminService {
    @Resource
    FileService fileService;
    @Resource
    PasswordEncoder passwordEncoder;
    @Resource
    UmsResourceService umsResourceService;
    @Resource(name = "rt")
    RedisTemplate<String,Object> redisTemplate;
    public IPage list(String searchVal,Integer pageNo,Integer pageSize,Boolean active){
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(searchVal)){
            wrapper.like("name",searchVal);
        }
        if (null!=active){
            wrapper.eq("active",active);

        }
        return this.page(new Page<>(pageNo,pageSize),wrapper);
    }
    @Override
    public boolean add(String name, String password, String gender, LocalDate birthday, MultipartFile file, String phone, String email){


        UmsAdmin umsAdmin = new UmsAdmin(name,passwordEncoder.encode(password),gender,birthday,fileService.upload(file), Entityutil.uuid(),phone,email);

        return this.save(umsAdmin);
    }

    @Override
    public boolean delete(Long id, Boolean active) {
//        UmsAdmin admin = GetOne(id);
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setId(id);
        umsAdmin.setActive(active);
        return this.updateById(umsAdmin);
    }

    @Override
    public boolean update(Long id,String name, String gender, LocalDate birthday, MultipartFile file, String phone, String email) {
//        UmsAdmin admin=GetOne(id);
        UmsAdmin umsAdmin = new UmsAdmin(name,gender,birthday,phone,email);
        umsAdmin.setId(id);
        if (file!=null){
            umsAdmin.setIcon(fileService.upload(file));
        }
        return  this.updateById(umsAdmin);  //如果遇见某个属性是空的话，不是把数据库的值改成空指针而是不改她
    }

    @Override
    public List<UmsAdmin> getActive() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("active",1);
        return this.list(wrapper) ;
    }
    @Override
    public Map<String,Object> login(String username, String password) {
        HashMap map = new HashMap<>();
        UmsAdmin umsAdmin = this.matchLogin(username, password);
        List<UmsResource> menu = this.listByUser(umsAdmin.getUid());
        System.out.println(menu);
        map.put("token",Entityutil.JwtEncoder(umsAdmin));
        map.put("menu",menu);
        return map;
    }
    //匹配用户
    private UmsAdmin matchLogin(String username, String password){
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper();
        wrapper.eq("phone", username)
                .or()
                .eq("email", username);
        UmsAdmin admin = this.getOne(wrapper);
        if (admin==null || !passwordEncoder.matches(password,admin.getPassword())){
            throw new CxlException("登录名或者密码错误");
        }
        if (!admin.getActive()){
            throw new CxlException("该用户已被禁用无法登录！！");
        }

        return admin;
    }
    //查询权限
    private List<UmsResource> listByUser(String userUid){
        List<UmsResource> menu=new ArrayList();
        List<UmsResource> list = umsResourceService.getByUser(userUid);
        HashSet<String> backUrls = new HashSet();
        for (UmsResource umsResource:list){
            if (umsResource.getType().intValue()==0){
                backUrls.add(umsResource.getBackUrl());
                continue;
            }
            if (StringUtils.isBlank(umsResource.getParentUid())){
                menu.add(umsResource);
                continue;
            }
            for (UmsResource parent:list){
                if (umsResource.getParentUid().equals(parent.getUid())){
                    parent.getChildren().add(umsResource);
                    break;
                }
            }
        }
        redisTemplate.opsForValue().set(userUid,backUrls,30, TimeUnit.DAYS);

        return menu;
    }
    private UmsAdmin GetOne(Long id){
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        return this.getOne(wrapper);
    }
}
