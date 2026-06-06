package com.hotelos.roomservice.repository;

import com.hotelos.roomservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Bazaga buyurtmalarni yozish va o'qish uchun standart metodlar yetarli
}