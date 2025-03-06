package com.example.ticketSystem.bookingservice.service;

import com.example.ticketSystem.bookingservice.Client.InventoryServiceClient;
import com.example.ticketSystem.bookingservice.event.BookingEvent;
import com.example.ticketSystem.bookingservice.repository.CustomerRepository;
import com.example.ticketSystem.bookingservice.request.BookingRequest;
import com.example.ticketSystem.bookingservice.response.BookingResponse;
import com.example.ticketSystem.bookingservice.response.InventoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.example.ticketSystem.bookingservice.entity.Customer;

import java.math.BigDecimal;

@Service
@Slf4j
public class BookingService {

    private final CustomerRepository customerRepository;
    private final InventoryServiceClient inventoryServiceClient;
    private final KafkaTemplate<String, BookingEvent> kafkaTemplate; // key-> string, value ->BookingEvent

    @Autowired
    public BookingService(final CustomerRepository customerRepository,
                          final InventoryServiceClient inventoryServiceClient,
                          final KafkaTemplate<String, BookingEvent> kafkaTemplate){
        this.customerRepository = customerRepository;
        this.inventoryServiceClient = inventoryServiceClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    public BookingResponse createBooking(final BookingRequest request) {
        // check if user exists
        final Customer customer = customerRepository.findById(request.getUserId()).orElse(null);
        if(customer ==null){
            throw new RuntimeException("User not found");
        }
        // check if there is enough inventory
        final InventoryResponse inventoryResponse = inventoryServiceClient.getInventory(request.getEventId());
        log.info("Inventory Response: {}",inventoryResponse);
        if(inventoryResponse.getCapacity() <  request.getTicketCount()){
            throw new RuntimeException("Not enough tickets available");
        }
        // create booking
        final BookingEvent bookingEvent = createBookingEvent(request,customer,inventoryResponse);
        // send booking to order service on a kafka topic
        kafkaTemplate.send("bookingEvent",bookingEvent);
        log.info("Booking sent to Kakfa: {}",bookingEvent);

        return BookingResponse.builder()
                .userId(bookingEvent.getUserId())
                .eventId(bookingEvent.getEventId())
                .ticketCount(bookingEvent.getTicketCount())
                .totalPrice(bookingEvent.getTotalPrice())
                .build();
    }

    private BookingEvent createBookingEvent(final BookingRequest request,
                                            final Customer customer,
                                            final InventoryResponse inventoryResponse){
        return BookingEvent.builder()
                .userId(customer.getId())
                .eventId(request.getEventId())
                .ticketCount(request.getTicketCount())
                .totalPrice(inventoryResponse.getTicketPrice().multiply(BigDecimal.valueOf(request.getTicketCount())))
                .build();

    }
}
