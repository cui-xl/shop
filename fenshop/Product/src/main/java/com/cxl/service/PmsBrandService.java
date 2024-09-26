package com.cxl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cxl.entity.PmsBrand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.org.apache.xpath.internal.operations.Mult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author cxl
 * @since 2023-10-19
 */
public interface PmsBrandService extends IService<PmsBrand> {
    IPage<PmsBrand> List(Integer pageNo, Integer pageSize,String searchVal,Boolean active);
    Boolean add(String name, MultipartFile Logo, String description);
    Boolean update(Long id,String name,MultipartFile Logo,String description);
    Boolean del(Long id,Boolean active);
    List<PmsBrand> getAll();
    String getNameByUid(String uid);
}
