package com.cxl.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cxl.core.BaseEntity;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * 文件表
 * </p>
 *
 * @author cxl
 * @since 2023-10-17
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("ums_file")
public class UmsFile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 文件md5
     */
    private String md5;

    /**
     * 大小
     */
    private Long size;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件路径
     */
    private String path;


}
