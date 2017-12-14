package com.brother.bysf.by.sf.web.cache;

import com.brother.bysf.by.sf.web.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sk-shifanwen
 * @date 2017/12/14
 */
@Component
@EnableCaching
public class UserInfoManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoManager.class);

    static List<User> users = new ArrayList<>();
    {
        User user = new User();
        user.setUserId("u-001");
        user.setUserName("n-001");
        users.add(user);
    }

    @Cacheable(value = "user_info", key = "#userId")
    public User findById(final String userId) {
        LOGGER.info("cache miss, invoke find by userId, userId:" + userId);
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    @CachePut(value = "user_info", key = "#user.userId")
    public User save(User user) {
        LOGGER.info("put user:" + user.toString());
        users.add(user);
        return user;
    }

    @CacheEvict(value = "user_info", key = "#userId")
    public String delete(final String userId) {
        User user = findById(userId);
        LOGGER.info("delete user:" + user);
        users.remove(user);
        return userId;
    }

    @CacheEvict(value = "user_info", allEntries = true)
    public void deleteAll() {
        LOGGER.info("deleteAll user");
        users.clear();
    }
}
