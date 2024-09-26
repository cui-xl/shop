package com.cxl.test;

import com.alibaba.fastjson.JSONObject;
import com.cxl.ProductApp;
import com.cxl.elasticsearch.Good;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchExtBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.AbstractHighlighterBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.swing.text.Highlighter;
import java.io.IOException;
import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductApp.class)
public class ElasticTest {
    @Resource
    RestHighLevelClient client;
    @Test
    public void createIndex() throws IOException {
        CreateIndexRequest request=new CreateIndexRequest("good");
        request.mapping("{\n" +
                "    \"properties\": {\n" +
                "      \"id\":{\n" +
                "        \"type\":\"long\"\n" +
                "      },\n" +
                "      \"name\":{\n" +
                "        \"type\":\"text\",\n" +
                "        \"analyzer\": \"ik_max_word\"\n" +
                "      },\n" +
                "      \"src\":{\n" +
                "        \"type\":\"keyword\"\n" +
                "      },\n" +
                "      \"price\":{\n" +
                "        \"type\": \"float\"\n" +
                "      },\n" +
                "      \"isNew\":{\n" +
                "        \"type\":\"boolean\"\n" +
                "      },\n" +
                "      \"isHot\":{\n" +
                "        \"type\":\"boolean\"\n" +
                "      },\n" +
                "      \"isPublish\":{\n" +
                "        \"type\":\"boolean\"\n" +
                "      },\n" +
                "      \"active\":{\n" +
                "        \"type\":\"boolean\"\n" +
                "      }\n" +
                "    }\n" +
                "  }", XContentType.JSON);
        client.indices().create(request, RequestOptions.DEFAULT);

    }
    @Test
    public void add() throws IOException {
        IndexRequest request = new IndexRequest("good");
        request.id("3");
        Good good = new Good(3L,
                "x90",
                "http://192.168.31.128:9000/123/a2f431cbd1e0478fbb3889f0cbba2fbf.jfif",
                new BigDecimal("4999.00"),
                false,
                true,
                true,
                true);
        request.source(JSONObject.toJSONString(good),XContentType.JSON);
        client.index(request,RequestOptions.DEFAULT);


    }
    @Test
    public void query(){
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("name").preTags("<strong style='color:#ffff00'")
                .postTags("</strong>");
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder booleanQuery = QueryBuilders.boolQuery();
        searchSourceBuilder.highlighter(highlightBuilder);
        searchSourceBuilder.query(booleanQuery);
        searchSourceBuilder.query(QueryBuilders.matchQuery("name",""));


    }

}
