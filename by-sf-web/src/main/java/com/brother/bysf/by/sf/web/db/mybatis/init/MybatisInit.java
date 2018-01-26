package com.brother.bysf.by.sf.web.db.mybatis.init;

import com.alibaba.fastjson.JSON;
import com.brother.bysf.by.sf.web.db.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author sk-shifanwen
 * @date 2018/1/19
 */
@Component
public class MybatisInit {
    @Autowired
    UserMapper userMapper;

//    @PostConstruct
    private void init() {
        dataFind();
    }

    private void dataFind() {
        System.out.println("userMapper.getByUserName(还荇): " + JSON.toJSONString(userMapper.getByUserName("还荇"), true));
        System.out.println("userMapper.get(还荇, 23): " + JSON.toJSONString(userMapper.get("还荇", 23), true));
    }
}
