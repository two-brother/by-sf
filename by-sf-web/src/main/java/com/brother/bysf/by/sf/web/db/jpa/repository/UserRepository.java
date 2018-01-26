package com.brother.bysf.by.sf.web.db.jpa.repository;

import com.brother.bysf.by.sf.web.db.jpa.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author sk-shifanwen
 * @date 2018/1/17
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor {
    List<User> findByUserName(String userName);

    List<User> findByHobby(String hobby, Pageable pageable);

    @Query(value = "select * from spdj_user u where u.user_address = ?1 limit 5", nativeQuery = true)
    List<User> listByUserAddress(String userAddress);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update User u set u.balance = u.balance + :balance where u.id = :id")
    void updateBalanceById(@Param(value = "id") Long id, @Param(value = "balance") double balance);

}
