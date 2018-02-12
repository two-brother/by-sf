/*
package com.brother.bysf.by.sf.search.config;

import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.RequestLine;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

*/
/**
 * @author sk-shifanwen
 * @date 2018/2/1
 *//*

public class LowLevelRestClient {
    private static RestClient restClient = RestClient.builder(new HttpHost("10.10.13.51", 9200, "http"))
            .setFailureListener(new RestClient.FailureListener() {
                    @Override
                    public void onFailure(HttpHost host) {
                        System.err.println("err: " + host.getHostName() + ":" + host.getPort());
                    }
            }).build();

    private static void getRootTest() throws IOException {
        Response response = restClient.performRequest("GET", "/");
        System.out.println("es: root info: " + EntityUtils.toString(response.getEntity()));
    }

    private static void putTest() throws IOException {
        Map<String, String> params = Collections.emptyMap();
        String jsonString1 = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2018-02-01\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        String jsonString2 = "{" +
                "\"user\":\"kimchy-2\"," +
                "\"postDate\":\"2018-02-02\"," +
                "\"message\":\"trying out Elasticsearch 2\"" +
                "}";
        HttpEntity entity1 = new NStringEntity(jsonString1, ContentType.APPLICATION_JSON);
        HttpEntity entity2 = new NStringEntity(jsonString2, ContentType.APPLICATION_JSON);
        Response response1 = restClient.performRequest("PUT", "/posts/doc/1", params, entity1);
        Response response2 = restClient.performRequest("PUT", "/posts/doc/2", params, entity2);
        System.out.println("es: put1 result: " + EntityUtils.toString(response1.getEntity()));
        System.out.println("es: put2 result: " + EntityUtils.toString(response2.getEntity()));
    }

    private static void getTest() throws IOException {
        Response response = restClient.performRequest("GET", "/posts/doc/1");
        System.out.println("es: get result: " + EntityUtils.toString(response.getEntity()));
    }

    private static void consumerFactoryTest() throws IOException {
        Map<String, String> params = Collections.emptyMap();
        HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory consumerFactory =
                new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(30 * 1024 * 1024);
        Response response = restClient.performRequest("GET", "/posts/_search", params, null, consumerFactory);
        System.out.println("es: consumerFactoryTest: " + EntityUtils.toString(response.getEntity()));
    }

    private static void responseListenerTest(){
        ResponseListener responseListener = new ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                try {
                    System.out.println("es: responseListenerTest: " + EntityUtils.toString(response.getEntity()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception exception) {
                exception.printStackTrace();
            }
        };
        restClient.performRequestAsync("GET", "/posts/doc/1", responseListener);
        Map<String, String> params = Collections.singletonMap("pretty", "true");
        restClient.performRequestAsync("GET", "/posts/doc/2", params, responseListener);
    }

    private static void asyncTest(HttpEntity[] documents) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(documents.length);
        for (int i = 0; i < documents.length; i++) {
            restClient.performRequestAsync(
                    "PUT",
                    "/posts/doc/" + i,
                    Collections.<String, String>emptyMap(),
                    //let's assume that the documents are stored in an HttpEntity array
                    documents[i],
                    new ResponseListener() {
                        @Override
                        public void onSuccess(Response response) {
                            latch.countDown();
                            try {
                                System.out.println("es: performRequestAsync: " + EntityUtils.toString(response.getEntity()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Exception exception) {
                            latch.countDown();
                            exception.printStackTrace();
                        }
                    }
            );
        }
        latch.await();
    }

    private static HttpEntity[] fakeDocuments(){
        int documentCount = 100;
        HttpEntity[] documents = new HttpEntity[documentCount];
        for (int i = 0; i < documentCount; i++) {
            String document = "{" +
                    "\"user\":\"user-" + i + "\"," +
                    "\"postDate\":\"2018-02-02\"," +
                    "\"message\":\"trying out Elasticsearch\"" +
                    "}";
            documents[i] = new NStringEntity(document, ContentType.APPLICATION_JSON);
        }
        return documents;
    }

    private static void headerTest() throws IOException {
        Response response = restClient.performRequest("GET", "/", new BasicHeader("header", "value"));
        RequestLine requestLine = response.getRequestLine();
        HttpHost host = response.getHost();
        int statusCode = response.getStatusLine().getStatusCode();
        Header[] headers = response.getHeaders();
        String responseBody = EntityUtils.toString(response.getEntity());
        System.out.println("es: requestLine: " + requestLine);
        System.out.println("es: host: " + host);
        System.out.println("es: statusCode: " + statusCode);
        System.out.println("es: headers: " + JSON.toJSONString(headers));
        System.out.println("es: responseBody: " + responseBody);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("------------- start -------------");
        getRootTest();
        putTest();
        getTest();
        consumerFactoryTest();
        responseListenerTest();
        asyncTest(fakeDocuments());
        headerTest();
        restClient.close();
        System.out.println("------------- end -------------");
    }
}
*/
