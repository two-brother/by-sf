package com.brother.bysf.by.sf.web.job.config;

import com.brother.bysf.by.sf.web.job.CronTestJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shifanwen on 2017/10/19.
 */
@Configuration
public class CronTestJobConfig {

    @Autowired
    private ZookeeperRegistryCenter regCenter;

    @Bean
    public CronTestJob createEJob() {
        return new CronTestJob();
    }


    @Bean(initMethod = "init")
    public JobScheduler createjobSchedular(final CronTestJob cronTestJob) {
        String cron = "0/5 * * * * ?";
        int shareTotalCount = 1;

        //job配置
        LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(
                new SimpleJobConfiguration(
                        JobCoreConfiguration.newBuilder(cronTestJob.getClass().getName(), cron, shareTotalCount).build(),
                        cronTestJob.getClass().getCanonicalName()
                )
        ).overwrite(true).build();

        return new SpringJobScheduler(cronTestJob, regCenter, liteJobConfiguration);
    }
}
