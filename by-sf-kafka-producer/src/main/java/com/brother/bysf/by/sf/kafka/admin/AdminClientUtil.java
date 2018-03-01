package com.brother.bysf.by.sf.kafka.admin;

import com.brother.bysf.by.sf.kafka.producer.KafkaProperties;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.config.TopicConfig;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @author sk-shifanwen
 * @date 2018/3/1
 */
public class AdminClientUtil {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);

        AdminClient adminClient = AdminClient.create(props);
        ConfigResource resource = new ConfigResource(ConfigResource.Type.TOPIC, KafkaProperties.TOPIC);

        // get the current topic configuration
        DescribeConfigsResult describeConfigsResult  = adminClient.describeConfigs(Collections.singleton(resource));
        Map<ConfigResource, Config> config = describeConfigsResult.all().get();

        System.out.println(config);

        // create a new entry for updating the retention.ms value on the same topic
        ConfigEntry retentionEntry = new ConfigEntry(TopicConfig.RETENTION_MS_CONFIG, KafkaProperties.TOPIC_CONFIG_RETENTION_MS_CONFIG);
        Map<ConfigResource, Config> updateConfig = new HashMap();
        updateConfig.put(resource, new Config(Collections.singleton(retentionEntry)));

        AlterConfigsResult alterConfigsResult = adminClient.alterConfigs(updateConfig);
        alterConfigsResult.all();

        describeConfigsResult  = adminClient.describeConfigs(Collections.singleton(resource));
        config = describeConfigsResult.all().get();

        System.out.println(config);

        adminClient.close();
    }
}
