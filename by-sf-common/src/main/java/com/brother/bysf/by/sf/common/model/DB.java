package com.brother.bysf.by.sf.common.model;

import java.util.List;

/**
 * @author sk-shifanwen
 * @date 2018/9/18
 */
public class DB {
    private String name;
    private List<Table> tables;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    @Override
    public String toString() {
        return "DB{" +
                "name='" + name + '\'' +
                ", tables=" + tables +
                '}';
    }
}
