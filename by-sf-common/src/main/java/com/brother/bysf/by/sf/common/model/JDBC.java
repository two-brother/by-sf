package com.brother.bysf.by.sf.common.model;

/**
 * @author sk-shifanwen
 * @date 2018/9/18
 */
public class JDBC {
    private String driver;
    private String jdbcUrl;
    private String jdbcUserName;
    private String jdbcPassword;
    private String dbName;

    public JDBC(String driver, String jdbcUrl, String jdbcUserName, String jdbcPassword, String dbName) {
        this.driver = driver;
        this.jdbcUrl = jdbcUrl;
        this.jdbcUserName = jdbcUserName;
        this.jdbcPassword = jdbcPassword;
        this.dbName = dbName;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUserName() {
        return jdbcUserName;
    }

    public void setJdbcUserName(String jdbcUserName) {
        this.jdbcUserName = jdbcUserName;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public String toString() {
        return "JDBC{" +
                "driver='" + driver + '\'' +
                ", jdbcUrl='" + jdbcUrl + '\'' +
                ", jdbcUserName='" + jdbcUserName + '\'' +
                ", jdbcPassword='" + jdbcPassword + '\'' +
                ", dbName='" + dbName + '\'' +
                '}';
    }
}
