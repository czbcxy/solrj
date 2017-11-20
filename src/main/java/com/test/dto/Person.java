package com.test.dto;

import org.apache.solr.client.solrj.beans.Field;

/**
 * @author chengzhengbo
 * @Date 2017/11/20
 * @TIME 上午11:37
 */
public class Person {

    /**
     * @Field的作用 作用1：指定Bean的一个字段为Field
     */

    @Field("id")
    private String id;

    @Field("title")
    private String title;

    @Field("url")
    //必须到schema.xml配置必需有url这个field，不然会报错。
    // <field name="s_url" type="string" indexed="true" stored="true" multiValued="false" default=""/>
    private String url;

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
