package com.brother.bysf.by.sf.mongo.repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.Serializable;
import java.util.List;

/**
 * @author sk-shifanwen
 * @date 2018/4/13
 */
public interface CommonRepository<T, ID extends Serializable> extends MongoRepository<T, ID> {

    /**
     * map reduce
     * @param mongoTemplate mongo模板
     * @param query 查询条件
     * @param inputType 输入类型
     * @param mapFunction map方法
     * @param reduceFunction reduce方法
     * @param outputType 输出类型
     * @return MapReduceResults
     */
    default MapReduceResults<T> mapReduce(MongoTemplate mongoTemplate, Query query, Class<T> inputType, String mapFunction,
                                              String reduceFunction, Class<T> outputType){
        String collectionName = mongoTemplate.getCollectionName(inputType);
        return mongoTemplate.mapReduce(query, collectionName,
                mapFunction,
                reduceFunction,
                outputType);
    }

    /**
     * aggregate
     * @param mongoTemplate mongo模板
     * @param aggregation 聚合函数
     * @param inputType 输入类型
     * @param outputType 输出类型
     * @return List<T>
     */
    default List<T> aggregate(MongoTemplate mongoTemplate, Aggregation aggregation, Class<?> inputType, Class<T> outputType){
        AggregationResults<T> result = mongoTemplate.aggregate(aggregation, inputType, outputType);
        return result.getMappedResults();
    }
}
