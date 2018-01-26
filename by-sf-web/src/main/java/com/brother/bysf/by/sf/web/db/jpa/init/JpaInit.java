package com.brother.bysf.by.sf.web.db.jpa.init;

import com.alibaba.fastjson.JSON;
import com.brother.bysf.by.sf.web.db.jpa.entity.Order;
import com.brother.bysf.by.sf.web.db.jpa.entity.User;
import com.brother.bysf.by.sf.web.db.jpa.repository.OrderRepository;
import com.brother.bysf.by.sf.web.db.jpa.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.brother.bysf.by.sf.web.util.FakeUtil.getAddress;
import static com.brother.bysf.by.sf.web.util.FakeUtil.getHobby;
import static com.brother.bysf.by.sf.web.util.FakeUtil.getName;

/**
 * @author sk-shifanwen
 * @date 2018/1/17
 */
@Component
public class JpaInit {
    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    //@PostConstruct
    private void init() throws UnsupportedEncodingException {
        dataInit();
        dataFind();
        dataCustomer();
    }

    private void dataInit() throws UnsupportedEncodingException {
        System.out.println("========= init start ==============");
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setUserName(getName());
            user.setAge(new Random().nextInt(150));
            user.setUserAddress(getAddress());
            user.setHobby(getHobby());
            user.setBalance(new Random().nextDouble());
            userList.add(user);
        }
        userRepository.save(userList);

        Page<User> userPage = userRepository.findAll(new PageRequest(0,5));
        userPage.forEach(user -> {
            Order order = new Order();
            order.setUser(user);
            orderRepository.save(order);
        });

        System.out.println("========= init end ==============");
    }

    private void dataFind() throws UnsupportedEncodingException {
        System.out.println("========= find start ==============");

        System.out.println("findOne(100L): " + JSON.toJSONString(userRepository.findOne(100L), true));

        System.out.println("findByUserName(\"杜淘\"): " + JSON.toJSONString(userRepository.findByUserName("杜淘"), true));;

        Pageable pageable = new PageRequest(0,5);
        System.out.println("findByHobby(\"写代码\",  new PageRequest(0,5)): " + JSON.toJSONString(userRepository.findByHobby("写代码", pageable), true));

        System.out.println("listByUserAddress(\"锦江区\"): " + JSON.toJSONString(userRepository.listByUserAddress("锦江区"), true));

        userRepository.updateBalanceById(1L, 0.17D);

        System.out.println("findByUser_IdAndId(1L,1L): " + JSON.toJSONString(orderRepository.findByUser_IdAndId(1L,1L), true));

        List<Integer> ageList = new ArrayList<>();
        ageList.add(48);
        ageList.add(105);
        ageList.add(32);
        System.out.println("findByUser_Age(48,105,32): " + JSON.toJSONString(orderRepository.findByUser_AgeIn(ageList), true));

        System.out.println("========= find end ==============");
    }

    private void dataCustomer(){
        System.out.println("========= data customer start ==============");
        User user = new User();
        user.setAge(100);
        user.setUserAddress("市");
        Pageable pageable = new PageRequest(0, 10);
        System.out.println("findUserByCondition: " + JSON.toJSONString(userRepository.findAll(CustomerSpecs.findUserByCondition(user), pageable), true));

        Order order = new Order();
        order.setId(1L);
        order.setUser(user);
        System.out.println("findOrderByCondition: " + JSON.toJSONString(userRepository.findAll(CustomerSpecs.findOrderByCondition(order), pageable), true));
        System.out.println("========= data customer end ==============");
    }

}

class CustomerSpecs {
    static Specification<User> findUserByCondition(User user) {
        Assert.notNull(user, "user must not be null");
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (null != user.getAge()){
                predicates.add(builder.gt(root.get("age").as(Integer.class), user.getAge()));
            }
            if (StringUtils.isNotBlank(user.getUserAddress())){
                predicates.add(builder.like(root.get("userAddress").as(String.class), "%" + user.getUserAddress()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    static Specification<Order> findOrderByCondition(Order order) {
        Assert.notNull(order, "order must not be null");
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (null != order.getId()){
                predicates.add(builder.equal(root.get("id").as(Long.class), order.getId()));
            }
            if (StringUtils.isNotBlank(order.getUser().getUserAddress())){
                predicates.add(builder.like(root.get("userAddress").as(String.class), "%" + order.getUser().getUserAddress()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}