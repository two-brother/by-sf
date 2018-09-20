package com.brother.bysf.by.sf.common.model;

import java.util.List;

/**
 * @author sk-shifanwen
 * @date 2018/9/18
 */
public class Table {
    private String  name;
    private List<Field> fields;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", fields=" + fields +
                '}';
    }
}
