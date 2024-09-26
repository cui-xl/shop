package com.cxl.service;

import com.cxl.entity.UmsFile;
import com.baomidou.mybatisplus.extension.service.IService;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 文件表 服务类
 * </p>
 *
 * @author cxl
 * @since 2023-10-17
 */
public interface UmsFileService extends IService<UmsFile> {
    String upload (String bucket, MultipartFile file) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException;

}
