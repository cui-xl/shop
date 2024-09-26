package com.cxl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cxl.elasticsearch.Good;

import java.io.IOException;

public interface GoodSearchService {
    IPage<Good> search(Integer pageNo,Integer pageSize,String name,Boolean active,Boolean isPublish) throws IOException;
}
