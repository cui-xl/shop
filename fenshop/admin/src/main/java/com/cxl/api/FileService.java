package com.cxl.api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@FeignClient("cxlfile")
public interface FileService {
    @PostMapping(value = "/umsFile/123/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String upload(MultipartFile file);
}
