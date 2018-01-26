package com.brother.bysf.by.sf.web.db.jpa.entity;

import javax.persistence.*;

/**
 * @author sk-shifanwen
 * @date 2018/1/18
 */
@Entity
@Table(name = "spdj_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
