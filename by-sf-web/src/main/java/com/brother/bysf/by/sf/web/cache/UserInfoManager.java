package com.brother.bysf.by.sf.web.cache;

import com.brother.bysf.by.sf.web.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author sk-shifanwen
 * @date 2017/12/14
 */
@Component
@EnableCaching
@CacheConfig(cacheManager = "cacheManagerRedis", cacheNames = {"user_info"} )
public class UserInfoManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoManager.class);

    @Cacheable(key = "#userId")
    public User findById(final String userId) {
        LOGGER.info("cache miss, invoke find by userId, userId:" + userId);
        User user = new User();
        user.setUserId("u-001");
        user.setUserName("n-001");
        return user;
    }

    @CachePut(key = "#user.userId")
    public User save(User user) {
        LOGGER.info("put user:" + user.toString());
        return user;
    }

    @CacheEvict(key = "#userId")
    public String delete(final String userId) {
        User user = findById(userId);
        LOGGER.info("delete user:" + user);
        return userId;
    }

    @CacheEvict(allEntries = true)
    public void deleteAll() {
        LOGGER.info("deleteAll user");
    }
}
