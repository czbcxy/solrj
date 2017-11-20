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

        doc.addField("id", "this is id");
        doc.addField("title", "this is document");

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
    public void queryTest(){
        String query = "*:*";
        SolrQuery solrQuery = new SolrQuery(query);

        solrQuery.set("rows",10);

        try {
            QueryResponse response = null;
            response = server.query(solrQuery);
            SolrDocumentList solrDocumentList = response.getResults();
            System.out.println("总共" +solrDocumentList.getNumFound()+"条记录");
            for (SolrDocument doc :solrDocumentList ) {
                System.out.println("id : " + doc.get("id") + "  title : " + doc.get("title"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void updateTest() {

        SolrInputDocument doc = new SolrInputDocument();

        doc.addField("id", "carver");
        doc.addField("title", "java cainiao");

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
