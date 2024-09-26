package com.cxl.mapper;

import com.cxl.entity.UmsResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author cxl
 * @since 2023-10-23
 */
public interface UmsResourceMapper extends BaseMapper<UmsResource> {
    List<UmsResource> getByUser(String userUid);
}
