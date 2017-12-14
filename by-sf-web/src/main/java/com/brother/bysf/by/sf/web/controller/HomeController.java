package com.brother.bysf.by.sf.web.controller;

import com.brother.bysf.by.sf.web.cache.UserInfoManager;
import com.brother.bysf.by.sf.web.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author sk-shifanwen
 * @date 2017/12/14
 */
@RestController
@RequestMapping(path = "/index")
public class HomeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    UserInfoManager userInfoManager;

    @GetMapping
    public ResponseEntity<?> index(){
        String msg = "welcome to brother home!";
        LOGGER.info(msg);
        return ResponseEntity.ok(msg);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> get(){
        return ResponseEntity.ok(userInfoManager.findById("u-001").toString());
    }

    @GetMapping(value = "/put")
    public ResponseEntity<?> put(){
        User user = new User();
        user.setUserId("u-put-001");
        user.setUserName("n-put-001");
        LOGGER.info(userInfoManager.save(user).toString());
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/delete")
    public ResponseEntity<?> delete(){
        return ResponseEntity.ok(userInfoManager.delete("u-001"));
    }

    @GetMapping(value = "/deleteAll")
    public ResponseEntity<?> deleteAll(){
        userInfoManager.deleteAll();
        return ResponseEntity.ok("ok");
    }
}
