/*
package com.brother.bysf.by.sf.search.config;



import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

*/
/**
 * @author sk-shifanwen
 * @date 2018/2/2
 *//*

public class HighLevelRestClient {
    public static RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("10.10.13.51", 9200, "http")));

    public static void createIndex(){
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("fy-sf-demo");
        String settings = "{\"analysis\": {\n" +
                "      \"analyzer\": {\n" +
                "        \"ip_custom_analyzer\": {\n" +
                "          \"type\": \"custom\",\n" +
                "          \"tokenizer\": \"punctuation\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"tokenizer\": {\n" +
                "        \"punctuation\": { \n" +
                "          \"type\": \"pattern\",\n" +
                "          \"pattern\": \"[ .,]\"\n" +
                "        }\n" +
                "      }\n" +
                "    }}";
        String  mapping = "{\n" +
                "    \"doc\":{\n" +
                "\t    \"properties\": {\n" +
                "\t\t\t\"internet_explorer\": {\n" +
                "\t\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\t\"analyzer\": \"standard\",\n" +
                "\t\t\t\t\"search_analyzer\": \"standard\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"address\": {\n" +
                "\t\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\t\"analyzer\": \"ik_max_word\",\n" +
                "\t\t\t\t\"search_analyzer\": \"ik_max_word\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"date\": {\n" +
                "\t\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\t\"analyzer\": \"standard\",\n" +
                "\t\t\t\t\"search_analyzer\": \"standard\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"url\": {\n" +
                "\t\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\t\"analyzer\": \"ik_max_word\",\n" +
                "\t\t\t\t\"search_analyzer\": \"ik_max_word\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"phone_number\": {\n" +
                "\t\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\t\"analyzer\": \"standard\",\n" +
                "\t\t\t\t\"search_analyzer\": \"standard\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"ip\": {\n" +
                "\t\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\t\"analyzer\": \"ip_custom_analyzer\",\n" +
                "\t\t\t\t\"search_analyzer\": \"ip_custom_analyzer\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"name\": {\n" +
                "\t\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\t\"analyzer\": \"ik_max_word\",\n" +
                "\t\t\t\t\"search_analyzer\": \"ik_max_word\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"paragraph\": {\n" +
                "\t\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\t\"analyzer\": \"ik_max_word\",\n" +
                "\t\t\t\t\"search_analyzer\": \"ik_max_word\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"job\": {\n" +
                "\t\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\t\"analyzer\": \"standard\",\n" +
                "\t\t\t\t\"search_analyzer\": \"standard\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"mac_address\": {\n" +
                "\t\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\t\"analyzer\": \"ik_max_word\",\n" +
                "\t\t\t\t\"search_analyzer\": \"ik_max_word\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
        createIndexRequest.settings(settings, XContentType.JSON);
        createIndexRequest.mapping("doc", mapping, XContentType.JSON);
        createIndexRequest.alias(new Alias("fy-sf-demo-alias"));
        createIndexRequest.masterNodeTimeout(TimeValue.timeValueMinutes(1));

        try {
            CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest);
            boolean acknowledged = createIndexResponse.isAcknowledged();
            boolean isShardsAcked = createIndexResponse.isShardsAcked();
            System.out.println("acknowledged: " + acknowledged);
            System.out.println("isShardsAcked: " + isShardsAcked);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args){
        System.out.println("------------- start -------------");
        createIndex();
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("------------- end -------------");
    }
}
*/
