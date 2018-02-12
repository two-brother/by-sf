package com.brother.bysf.by.sf.search.config;

/**
 * @author sk-shifanwen
 * @date 2018/2/11
 */
class Constant {
    static final String INDEX_TYPE = "doc";
    static final String INDEX_NAME_BY_SF_DEMO = "by-sf-demo";
    static final String BY_SF_DEMO_SETTINGS = "{" +
                                    "\"settings\": {" +
                                        "\"analysis\": {" +
                                            "\"analyzer\": {" +
                                                "\"ip_custom_analyzer\": {" +
                                                    "\"type\": \"custom\"," +
                                                    "\"tokenizer\": \"punctuation\"" +
                                                "}" +
                                            "}," +
                                            "\"tokenizer\": {" +
                                                "\"punctuation\": { " +
                                                    "\"type\": \"pattern\"," +
                                                    "\"pattern\": \"[ .,]\"" +
                                                "}" +
                                            "}" +
                                         "}" +
                                    "}" +
                                "}";
    static final String BY_SF_DEMO_MAPPING = "{" +
                                    "\"doc\": {" +
                                            "\"properties\": {" +
                                                "\"internet_explorer\": {" +
                                                    "\"type\": \"text\"," +
                                                    "\"analyzer\": \"standard\"," +
                                                    "\"search_analyzer\": \"standard\"" +
                                                "}," +
                                                "\"address\": {" +
                                                    "\"type\": \"text\"," +
                                                    "\"analyzer\": \"ik_max_word\"," +
                                                    "\"search_analyzer\": \"ik_max_word\"" +
                                                "}," +
                                                "\"date\": {" +
                                                    "\"type\": \"text\"," +
                                                    "\"analyzer\": \"standard\"," +
                                                    "\"search_analyzer\": \"standard\"" +
                                                "}," +
                                                "\"url\": {" +
                                                    "\"type\": \"text\"," +
                                                    "\"analyzer\": \"ik_max_word\"," +
                                                    "\"search_analyzer\": \"ik_max_word\"" +
                                                "}," +
                                                "\"phone_number\": {" +
                                                    "\"type\": \"text\"," +
                                                    "\"analyzer\": \"standard\"," +
                                                    "\"search_analyzer\": \"standard\"" +
                                                "}," +
                                                "\"ip\": {" +
                                                    "\"type\": \"text\"," +
                                                    "\"analyzer\": \"ip_custom_analyzer\"," +
                                                    "\"search_analyzer\": \"ip_custom_analyzer\"" +
                                                "}," +
                                                "\"name\": {" +
                                                    "\"type\": \"text\"," +
                                                    "\"analyzer\": \"ik_max_word\"," +
                                                    "\"search_analyzer\": \"ik_max_word\"" +
                                                "}," +
                                                "\"paragraph\": {" +
                                                    "\"type\": \"text\"," +
                                                    "\"analyzer\": \"ik_max_word\"," +
                                                    "\"search_analyzer\": \"ik_max_word\"" +
                                                "}," +
                                                "\"job\": {" +
                                                    "\"type\": \"text\"," +
                                                    "\"analyzer\": \"standard\"," +
                                                    "\"search_analyzer\": \"standard\"" +
                                                "}," +
                                                "\"mac_address\": {" +
                                                    "\"type\": \"text\"," +
                                                    "\"analyzer\": \"ik_max_word\"," +
                                                    "\"search_analyzer\": \"ik_max_word\"" +
                                                "}" +
                                            "}" +
                                        "}" +
                                    "}";


    static final String TEST_QUERY_ALL = "{" +
        "\"from\": 0, " +
        "\"size\": 10, " +
        "\"query\": {" +
            "\"match_all\": {}" +
        "}" +
    "}";
}
