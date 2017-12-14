package com.brother.bysf.by.sf.web.entity;

import java.io.Serializable;

/**
 * @author sk-shifanwen
 * @date 2017/12/14
 */
public class User implements Serializable{
    private String userId;
    private String userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
