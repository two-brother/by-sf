package com.brother.bysf.by.sf.web.db.mybatis.sql;

import com.brother.bysf.by.sf.web.db.jpa.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author sk-shifanwen
 * @date 2018/1/19
 */
@Component
public class UserSqlProvider {
    /**
     * 根据用户名和年龄查询
     * @param params
     * @return
     */
    public String get(Map<String, Object> params){
        String userName = (String) params.get("userName");
        Integer age = (Integer) params.get("age");
        SQL sql = new SQL().SELECT("*").FROM("spdj_user");
        if(!StringUtils.isEmpty(userName)){
            sql.WHERE("user_name=#{userName}");
        }
        if(null != age){
            sql.WHERE("age=#{age}");
        }
        return sql.toString();
    }

    /**
     * 修改用户名和年龄
     * @param user
     * @return
     */
    public String update(User user){
        SQL sql = new SQL().UPDATE("spdj_user").SET("user_name = #{userName}, age = #{age}")
                .WHERE("id=#{id}");
        return sql.toString();
    }
}
