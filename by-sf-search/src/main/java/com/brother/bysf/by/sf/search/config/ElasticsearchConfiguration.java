package com.brother.bysf.by.sf.search.config;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * @author sk-shifanwen
 * @date 2018/2/1
 */
public class ElasticsearchConfiguration {
    public static RestClient restClient = RestClient.builder(new HttpHost("10.10.13.51", 9200, "http"))
            .setFailureListener(new RestClient.FailureListener() {
                    @Override
                    public void onFailure(HttpHost host) {
                        System.err.println("err: " + host.getHostName() + ":" + host.getPort());
                    }
            }).build();

    public static void getRootTest() throws IOException {
        Response response = restClient.performRequest("GET", "/");
        System.out.println("es: root info: " + EntityUtils.toString(response.getEntity()));
    }

    public static void putTest() throws IOException {
        Map<String, String> params = Collections.emptyMap();
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2018-02-01\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
        Response response = restClient.performRequest("PUT", "/posts/doc/1", params, entity);
        System.out.println("es: put result: " + EntityUtils.toString(response.getEntity()));
    }

    public static void getTest() throws IOException {
        Response response = restClient.performRequest("GET", "/posts/doc/1");
        System.out.println("es: get result: " + EntityUtils.toString(response.getEntity()));
    }

    public static void consumerFactory() throws IOException {
        Map<String, String> params = Collections.emptyMap();
        HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory consumerFactory =
                new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(30 * 1024 * 1024);
        Response response = restClient.performRequest("GET", "/posts/_search", params, null, consumerFactory);
        System.out.println("es: consumerFactory: " + EntityUtils.toString(response.getEntity()));
    }

    public static void main(String[] args) throws IOException {
        getRootTest();
        putTest();
        getTest();
        consumerFactory();
        System.exit(0);
    }
}
