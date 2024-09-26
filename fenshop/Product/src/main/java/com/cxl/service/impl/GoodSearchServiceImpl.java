package com.cxl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxl.elasticsearch.Good;
import com.cxl.service.GoodSearchService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
@Component
public class GoodSearchServiceImpl implements GoodSearchService {
    @Resource
    RestHighLevelClient client;
    @Override
    public IPage<Good> search(Integer pageNo,Integer pageSize,String name, Boolean active, Boolean isPublish) throws IOException {
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("name").preTags("<strong style='color:#ffff00'")
                .postTags("</strong>");
        SearchRequest searchRequest = new SearchRequest("good");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder booleanQuery = QueryBuilders.boolQuery();
        if(StringUtils.isNotBlank(name)){
            booleanQuery.must(QueryBuilders.matchQuery("name",name));
        }
        if (null!=active){
            booleanQuery.must(QueryBuilders.matchQuery("active",active));
        }
        if (null!=isPublish){
            booleanQuery.must(QueryBuilders.matchQuery("isPublish",isPublish));
        }
        searchSourceBuilder.query(booleanQuery).from((pageNo-1)*pageSize).size(pageSize);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        searchSourceBuilder.highlighter(highlightBuilder);
        ArrayList list = new ArrayList();
        for (SearchHit hit:searchHits){
            Map<String, HighlightField> fields = hit.getHighlightFields();
            Good good = JSONObject.parseObject(hit.getSourceAsString(), Good.class);
            if (fields.containsKey("name")){
                HighlightField field = fields.get("name");
                good.setName(field.fragments()[0].toString());
            }

            list.add(good);
        }
        long total = hits.getTotalHits().value;
        long pages=total/pageSize;
        IPage page=new Page();
        page.setRecords(list);
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        page.setTotal(total);
        page.setPages(total%pageSize==0?pages:pages+1);
        return page;
    }
}
