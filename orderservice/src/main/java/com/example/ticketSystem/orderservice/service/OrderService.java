package com.example.ticketSystem.orderservice.service;

import com.example.ticketSystem.bookingservice.event.BookingEvent;
import com.example.ticketSystem.orderservice.client.InventoryServiceClient;
import com.example.ticketSystem.orderservice.entity.Order;
import com.example.ticketSystem.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {


    private final OrderRepository orderRepository;
    private final InventoryServiceClient inventoryServiceClient;

    @Autowired
    public OrderService(OrderRepository orderRepository, InventoryServiceClient inventoryServiceClient) {
        this.orderRepository = orderRepository;
        this.inventoryServiceClient = inventoryServiceClient;
    }

    @KafkaListener(topics = "booking",groupId = "order-service")
    public void orderEvent(BookingEvent bookingEvent){
        log.info("Received order event: {}" , bookingEvent);

        //Create Order object for db
        Order order = createOrder(bookingEvent);
        orderRepository.saveAndFlush(order);

        //update inventory
        inventoryServiceClient.updateInventory(order.getEventId(),order.getTicketCount());
        log.info("Inventory updated for event: {}, less tickets:{}", order.getEventId(), order.getTicketCount());
    }

    private Order createOrder(BookingEvent bookingEvent){
        return Order.builder()
                .customerId(bookingEvent.getUserId())
                .eventId(bookingEvent.getEventId())
                .ticketCount(bookingEvent.getTicketCount())
                .totalPrice(bookingEvent.getTotalPrice())
                .build();
    }
}
