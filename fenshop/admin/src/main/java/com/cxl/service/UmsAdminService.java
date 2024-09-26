package com.cxl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cxl.entity.UmsAdmin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.corba.se.spi.ior.ObjectKey;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.org.apache.xpath.internal.operations.Mult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员表 服务类
 * </p>
 *
 * @author cxl
 * @since 2023-10-10
 */
public interface UmsAdminService extends IService<UmsAdmin> {
public IPage<UmsAdmin> list(String searchVal, Integer pageNo, Integer pageSize,Boolean active);
public boolean add(String name, String password, String gender, LocalDate birthday, MultipartFile file, String phone, String email);
boolean delete(Long id,Boolean active);
boolean update(Long id, String name, String gender, LocalDate birthday, MultipartFile icon, String phone, String email);
List<UmsAdmin> getActive();
Map<String, Object> login(String name, String password);

}



