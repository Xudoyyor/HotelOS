package com.hotelos.roomservice.service;

import com.hotelos.roomservice.component.DashboardNotifier;
import com.hotelos.roomservice.config.RabbitMQConfig;
import com.hotelos.roomservice.dto.OrderChargeEvent;
import com.hotelos.roomservice.dto.OrderRequest;
import com.hotelos.roomservice.enums.OrderStatus;
import com.hotelos.roomservice.exceptions.ResourceNotFoundException;
import com.hotelos.roomservice.model.Order;
import com.hotelos.roomservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Xona xizmati buyurtmalarini boshqaruvchi asosiy servis.
 * Paradigmalar: HODISAGA ASOSLANGAN (dashboard + billing nashri),
 * MA'LUMOTLAR TUZILMALARI (FIFO KitchenQueue), STATE MACHINE (OrderStatus).
 */
@Service
@RequiredArgsConstructor
public class RoomOrderService {

    private static final Logger log = LoggerFactory.getLogger(RoomOrderService.class);

    private final OrderRepository orderRepository;
    private final MenuService menuService;
    private final KitchenQueue kitchenQueue;
    private final DashboardNotifier dashboardNotifier;
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public Order createOrder(OrderRequest request) {
        BigDecimal total = menuService.calculateTotal(request.items());
        String description = menuService.buildDescription(request.items());

        Order order = Order.builder()
                .roomId(request.roomId())
                .roomNumber(request.roomNumber())
                .items(description)
                .totalAmount(total)
                .status(OrderStatus.QABUL_QILINDI)
                .createdAt(LocalDateTime.now())
                .build();

        Order saved = orderRepository.save(order);
        kitchenQueue.enqueue(saved.getId());
        log.info("Yangi buyurtma #{} qabul qilindi. Oshxona navbati: {}", saved.getId(), kitchenQueue.size());

        dashboardNotifier.order(saved.getRoomNumber(), saved.getStatus().name(), description, total);
        return saved;
    }

    /** Oshxona navbatidagi (FIFO) keyingi buyurtmani tayyorlashga oladi. */
    @Transactional
    public Order processNextOrder() {
        Long nextId = kitchenQueue.pollNext();
        if (nextId == null) {
            return null;
        }
        return updateOrderStatus(nextId, OrderStatus.TAYYORLANMOQDA);
    }

    @Transactional
    public Order updateOrderStatus(Long id, OrderStatus newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Buyurtma topilmadi: " + id));

        if (!order.getStatus().canTransitionTo(newStatus)) {
            throw new IllegalStateException("Holatni '" + order.getStatus()
                    + "' dan '" + newStatus + "' ga o'tkazib bo'lmaydi.");
        }

        order.setStatus(newStatus);

        if (newStatus == OrderStatus.YETKAZILDI) {
            order.setDeliveredAt(LocalDateTime.now());
            publishCharge(order);
        }

        Order saved = orderRepository.save(order);
        dashboardNotifier.order(saved.getRoomNumber(), saved.getStatus().name(), saved.getItems(),
                saved.getTotalAmount());
        return saved;
    }

    /** Buyurtma yetkazilgach reception servisiga to'lov hodisasini yuboradi. */
    private void publishCharge(Order order) {
        OrderChargeEvent event = OrderChargeEvent.builder()
                .roomNumber(order.getRoomNumber())
                .description("Xona xizmati: " + order.getItems())
                .amount(order.getTotalAmount())
                .build();
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.RK_ORDER_CHARGE, event);
        log.info("To'lov hodisasi yuborildi -> xona {} : {} so'm", order.getRoomNumber(), order.getTotalAmount());
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Buyurtma topilmadi: " + id));
    }
}