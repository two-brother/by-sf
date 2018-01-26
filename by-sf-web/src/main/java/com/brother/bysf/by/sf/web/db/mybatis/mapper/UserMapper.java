package com.brother.bysf.by.sf.web.db.mybatis.mapper;

import com.brother.bysf.by.sf.web.db.jpa.entity.User;
import com.brother.bysf.by.sf.web.db.mybatis.sql.UserSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author sk-shifanwen
 * @date 2018/1/19
 */
public interface UserMapper {
    @Select(value="select * from spdj_user where user_name = '${userName}'")
    @Results({
            @Result(property = "userAddress",  column = "user_address"),
            @Result(property = "userName", column = "user_name")
    })
    List<User> getByUserName(@Param("userName") String userName);

    @SelectProvider(type = UserSqlProvider.class, method = "get")
    @Results({
            @Result(property = "userAddress",  column = "user_address"),
            @Result(property = "userName", column = "user_name")
    })
    User get(@Param("userName") String userName, @Param("age") Integer age);

    @UpdateProvider(type = UserSqlProvider.class, method = "update")
    void update(User user);
}
