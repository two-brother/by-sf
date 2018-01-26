package com.brother.bysf.by.sf.web.db.jpa.repository;


import com.brother.bysf.by.sf.web.db.jpa.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author sk-shifanwen
 * @date 2018/1/18
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByUser_IdAndId(Long userId, Long orderId);

    List<Order> findByUser_AgeIn(List<Integer> ageList);
}
