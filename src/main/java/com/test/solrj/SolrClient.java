package com.test.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author chengzhengbo
 * @Date 2017/11/20
 * @TIME 上午11:31
 */
public class SolrClient {
    private static final String URL = "http://localhost:8080/solr-4.1.0/";

    private HttpSolrClient server = null;

    @Before
    public void init() {
        // 创建 server
        server = new HttpSolrClient(URL);
    }

    @Test
    public void addDoc() {

        SolrInputDocument doc = new SolrInputDocument();

        doc.addField("id", "id");
        doc.addField("title", "document");

        try {
            UpdateResponse response = server.add(doc);
            // 提交
            server.commit();

            System.out.println("########## Query Time :" + response.getQTime());
            System.out.println("########## Elapsed Time :" + response.getElapsedTime());
            System.out.println("########## Status :" + response.getStatus());

        } catch (Exception e) {
            System.err.print(e);
        }
    }


    /**
     * 查询
     */
    @Test
    public void queryTest() {
        String query = "*:*";
        SolrQuery solrQuery = new SolrQuery(query);

        //查询语法
//        solrQuery.set("q","id:carver");
        //过滤插叙
//        solrQuery.set("fq","id:carver");
        //排序
//        solrQuery.setSort("id", SolrQuery.ORDER.desc);
        //分页
//        int pageSize = 1;
//        int rows = 222;
//        int start = rows*(pageSize-1);
//        solrQuery.setStart(start);
//        solrQuery.setRows(rows);

        //开启高亮
        solrQuery.setHighlight(true);
        solrQuery.setQuery("title:cainiao");
        //高亮字段
        solrQuery.addHighlightField("title");
        //前缀标志
        solrQuery.setHighlightSimplePre("<span color=red>");
        //后缀标志
        solrQuery.setHighlightSimplePost("</span>");

        //用于调试程序
        System.out.println(solrQuery+"==========");
        try {
            //相应查询
            QueryResponse response = null;
            response = server.query(solrQuery);
            //匹配文档
            SolrDocumentList solrDocumentList = response.getResults();

            System.out.println("总共" + solrDocumentList.getNumFound() + "条记录");
            //从响应中获取高亮信息
            Map<String, Map<String, List<String>>> map = response.getHighlighting();

            for (SolrDocument doc : solrDocumentList) {
                System.out.println("id : " + doc.get("id") + "  title : " + doc.get("title"));
                //取出高亮信息
                if(map!=null){
                    //根据id取出高亮信息
                    String id = (String) doc.get("id");
                    Map<String, List<String>> idMap = map.get(id);
                    //取出高亮字段信息
                    List<String> title = idMap.get("title");
                    if(title!=null) {
                        for (int i = 0; i < title.size(); i++) {
                            System.out.println("title=====" + "  " + title.get(i));
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateTest() {

        SolrInputDocument doc = new SolrInputDocument();

        doc.addField("id", "czb");
        doc.addField("title", "java think ");

        try {
            UpdateResponse add = server.add(doc);
            System.out.println(add.getRequestUrl());
            System.out.println(add.getElapsedTime());
            System.out.println(add.getResponse());
            System.out.println(add.getElapsedTime());
            //查询
            queryTest();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
