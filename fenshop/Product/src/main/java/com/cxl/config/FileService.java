package com.cxl.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Configuration
@FeignClient("cxlfile")
public interface FileService {
    @PostMapping(value = "/umsFile/123/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String upload(MultipartFile file);
}
