package com.cxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxl.entity.UmsFile;
import com.cxl.mapper.UmsFileMapper;
import com.cxl.service.UmsFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxl.util.Entityutil;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author cxl
 * @since 2023-10-17
 */
@Service
public class UmsFileServiceImpl extends ServiceImpl<UmsFileMapper, UmsFile> implements UmsFileService {

    @Value("${minio.endpoint}")
    String endpoint;
    @Value("${minio.username}")
    String username;
    @Value("${minio.password}")
    String password;

    @Override
    public String upload(String bucket, MultipartFile file) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        //获取文件 MD5，大小，类型
        long size=file.getSize();
        String type = file.getContentType();
        String md5 = DigestUtils.md5Hex(file.getInputStream());
        UmsFile umsFile = this.get(md5, size, type);
        //不是空指针说明上传过
        if (null!=umsFile){
            return  umsFile.getPath();
        }
        MinioClient minioClient=MinioClient.builder()
                .endpoint(endpoint)
                .credentials(username,password)
                .build();
        StringBuilder fileName=new StringBuilder();
        fileName.append(Entityutil.uuid())
                .append(".")
                .append(FileNameUtils.getExtension(file.getOriginalFilename()));


        //创建上传的参数对象
        PutObjectArgs args=PutObjectArgs.builder()
                .bucket(bucket)
                .contentType(type)
                .object(fileName.toString())
                .stream(file.getInputStream(),size,0)
                .build();
        minioClient.putObject(args);
        StringBuilder path=new StringBuilder(endpoint);
        path.append("/").append(bucket).append("/").append(fileName.toString());
        umsFile=new UmsFile(md5,size,type,path.toString());
        this.save(umsFile);
        return path.toString();


    }
    private UmsFile get(String md5,long size,String type){
        QueryWrapper<UmsFile> wrapper = new QueryWrapper<>();
        wrapper.eq("md5",md5)
                .eq("size",size)
                .eq("type",type);
        return  this.getOne(wrapper);
    }
}
