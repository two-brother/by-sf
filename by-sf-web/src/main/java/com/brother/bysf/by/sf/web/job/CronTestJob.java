package com.brother.bysf.by.sf.web.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author sk-shifanwen
 * @date 2018/4/17
 */
public class CronTestJob implements SimpleJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(CronTestJob.class);
    @Override
    public void execute(ShardingContext shardingContext) {
        LOGGER.info("getTaskId: " + shardingContext.getTaskId());
        LOGGER.info("getJobName: " + shardingContext.getJobName());
        LOGGER.info("getJobParameter: " + shardingContext.getJobParameter());
        LOGGER.info("getShardingParameter: " + shardingContext.getShardingParameter());
        LOGGER.info("getShardingItem: " + shardingContext.getShardingItem());
        LOGGER.info("getShardingTotalCount: " + shardingContext.getShardingTotalCount());
        try {
            LOGGER.info("sleep 4 SECONDS");
            TimeUnit.SECONDS.sleep(4);
            LOGGER.info("wake up");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
