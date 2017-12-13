/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.brother.bysf.by.sf.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author sk-shifanwen
 */
public class KafkaConsumerDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerDemo.class);

    public static void main(String[] args) {

        int threadCount = Integer.parseInt(args.length < 1 ? "1" : args[0]);
        for (int i = 0; i < threadCount; i++) {
            Consumer consumerThread = new Consumer(KafkaProperties.TOPIC);
            consumerThread.start();
            LOGGER.info("thread[" + Thread.currentThread().getName() + "] started!");
        }

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("receive msg count:" + Consumer.receiveCount.get());
            }
        }, 1, 3, TimeUnit.SECONDS);
    }
}
