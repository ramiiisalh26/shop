package com.example.shop.order;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepositry extends JpaRepository<Order, UUID>{

    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.totalAmount = :totalAmount WHERE o.id = :id")
    void updateTotalAmount(@Param("totalAmount") double total_amount,@Param("id") UUID id);

    @Query("SELECT a FROM Order a WHERE a.shippingAddress.id = :addressId OR a.billingAddress.id = :addressId")
    List<Order> findOrdersByAddressId(@Param("addressId") UUID addressId);

    @Query("SELECT c FROM Order c WHERE c.customer.id = :customerId")
    List<Order> findOrdersByCustomerId(@Param("customerId") UUID customerId);
}
