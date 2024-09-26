package com.cxl.controller;

import com.cxl.service.UmsFileService;
import io.minio.errors.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.MessageUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 文件表 前端控制器
 * </p>
 *
 * @author cxl
 * @since 2023-10-17
 */
@RestController
@RequestMapping("/umsFile")
public class UmsFileController {
    @Resource
    UmsFileService umsFileService;
    @PostMapping("/{bucket}/upload")
    String upload(MultipartFile file,@PathVariable String bucket) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, ErrorResponseException, XmlParserException, InternalException {
        return umsFileService.upload(bucket,file);
    }

}
