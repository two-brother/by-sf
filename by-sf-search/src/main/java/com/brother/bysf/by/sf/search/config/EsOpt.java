package com.brother.bysf.by.sf.search.config;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.*;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.mapping.PutMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sk-shifanwen
 * @date 2018/2/9
 */
public class EsOpt {
    private final static Logger log = LoggerFactory.getLogger(EsOpt.class);

    private static JestClient client;

    static {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(
                new HttpClientConfig.Builder("http://10.10.13.51:9200")
                        .defaultCredentials("elastic", "S2Lylw_072nyLQxH@c3C")
                        .build()
        );
        client = factory.getObject();
    }

    static JestResult settings(String index, String settings) throws IOException {
        JestResult settingResult = client.execute(new CreateIndex.Builder(index).settings(settings).build());
        log.info("settingResult: " + settingResult.getJsonString());
        return settingResult;
    }

    static JestResult mapping(String index, String type, String mapping) throws IOException {
        JestResult mappingResult = client.execute(new PutMapping.Builder(index, type, mapping).build());
        log.info("mappingResult: " + mappingResult.getJsonString());
        return mappingResult;
    }

    static void createIndex(String index, String type, String settings, String mapping) throws IOException {
        JestResult settingResult = client.execute(new CreateIndex.Builder(index).settings(settings).build());
        log.info("settingResult: " + settingResult.getJsonString());

        JestResult mappingResult = client.execute(new PutMapping.Builder(index, type, mapping).build());
        log.info("mappingResult: " + mappingResult.getJsonString());
    }

    static JestResult createDocument(String indexName, String type, String source) throws IOException {
        Index index = new Index.Builder(source).index(indexName).type(type).build();
        return client.execute(index);
    }

    static JestResult bulk(String indexName, String typeName, List<Object> objList) throws IOException {
        Bulk.Builder bulk = new Bulk.Builder().defaultIndex(indexName).defaultType(typeName);
        for (Object obj : objList) {
            Index index = new Index.Builder(obj).build();
            bulk.addAction(index);
        }
        return client.execute(bulk.build());
    }

    static JestResult getDocument(String index, String type, String id) throws IOException {
        Get get = new Get.Builder(index, id).type(type).build();
        return client.execute(get);
    }

    static JestResult searchDocument(String index, String type, String query) throws IOException {
        Search search = new Search.Builder(query)
                .addIndex(index)
                .addType(type)
                .build();

        return client.execute(search);
    }

    static JestResult updateDocument(String index, String type, String id, String script) throws IOException {
        return client.execute(new Update.Builder(script).index(index).type(type).id(id).build());
    }

    static JestResult deleteDocument(String index, String type, String id) throws IOException {
        return client.execute(new Delete.Builder(id)
                .index(index)
                .type(type)
                .build());
    }

    public static void main(String[] args) throws IOException {
        String testDocument = "{" +
                "\"name\": \"仉红\"," +
                "\"job\": \"Researchscientist(physicalsciences)\"," +
                "\"phone_number\": \"14793843730\"," +
                "\"internet_explorer\": \"Mozilla/5.0(compatible;MSIE5.0;Windows95;Trident/3.0)\"," +
                "\"url\": \"https: //yan.org/\"," +
                "\"address\": \"西藏自治区琳县徐汇怀路i座233543\"," +
                "\"mac_address\": \"85:6a:aa:fb:68:7c\"," +
                "\"ip\": \"172.56.154.92\"," +
                "\"paragraph\": \"有关男人认为社区.出来一定女人记者都是地方经营.记者一次推荐开始广告.得到完成这么日期下载显示来源.最新组织这是就是.\"," +
                "\"date\": \"1976-05-20T15:50:16\"" +
                "}";
        String updateScript = "{" +
                "    \"script\" : {" +
                "        \"source\": \"ctx._source.ip = params.new_ip\"," +
                "        \"params\" : {" +
                "            \"new_ip\" : \"0.0.0.0\"" +
                "        }" +
                "    }" +
                "}";
        String upsertScript = "{" +
                "    \"scripted_upsert\":true," +
                "    \"script\" : {" +
                "      \"source\": \"ctx._source = params.testbody\"," +
                "        \"params\" : {" +
                "            \"testbody\" : {" +
                "                \"name\": \"仉红\"," +
                "\"job\": \"Researchscientist(physicalsciences)\"," +
                "\"phone_number\": \"14793843730\"," +
                "\"internet_explorer\": \"Mozilla/5.0(compatible;MSIE5.0;Windows95;Trident/3.0)\"," +
                "\"url\": \"https: //yan.org/\"," +
                "\"address\": \"西藏自治区琳县徐汇怀路i座233543\"," +
                "\"mac_address\": \"85:6a:aa:fb:68:7c\"," +
                "\"ip\": \"0.0.1.1\"," +
                "\"paragraph\": \"有关男人认为社区.出来一定女人记者都是地方经营.记者一次推荐开始广告.得到完成这么日期下载显示来源.最新组织这是就是.\"," +
                "\"date\": \"1976-05-20T15:50:16\"" +
                "            }" +
                "        }" +
                "    }," +
                "    \"upsert\" : {}" +
                "}";
        String index = Constant.INDEX_NAME_BY_SF_DEMO;
        String type = Constant.INDEX_TYPE;
        createIndex(index, type, Constant.BY_SF_DEMO_SETTINGS, Constant.BY_SF_DEMO_MAPPING);

        String id = createDocument(index, type, testDocument).getJsonObject().get("_id").getAsString();
        log.info("id== " + id);
        log.info("createDocument== " + createDocument(index, type, testDocument).getJsonString());
        log.info("getDocumentById== " + getDocument(index, type, id).getJsonString());
        log.info("updateDocument== " + updateDocument(index, type, id, updateScript).getJsonString());
        log.info("getDocumentById== " + getDocument(index, type, id).getJsonString());
        log.info("searchDocument== " + searchDocument(index, type, Constant.TEST_QUERY_ALL).getJsonString());
        log.info("deleteDocument== " + deleteDocument(index, type, id).getJsonString());
        log.info("upsertDocument== " + updateDocument(index, type, id, upsertScript).getJsonString());

        List<Object> sources = new ArrayList<>();
        sources.add(testDocument);
        sources.add(testDocument);
        log.info("bulk== " + bulk(index, type, sources).getJsonString());
    }
}
