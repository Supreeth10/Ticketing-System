package com.example.ticketSystem.bookingservice.service;

import com.example.ticketSystem.bookingservice.Client.InventoryServiceClient;
import com.example.ticketSystem.bookingservice.repository.CustomerRepository;
import com.example.ticketSystem.bookingservice.request.BookingRequest;
import com.example.ticketSystem.bookingservice.response.BookingResponse;
import com.example.ticketSystem.bookingservice.response.InventoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ticketSystem.bookingservice.entity.Customer;

@Service
public class BookingService {

    private final CustomerRepository customerRepository;
    private final InventoryServiceClient inventoryServiceClient;

    @Autowired
    public BookingService(final CustomerRepository customerRepository,
                          final InventoryServiceClient inventoryServiceClient){
        this.customerRepository = customerRepository;
        this.inventoryServiceClient = inventoryServiceClient;
    }

    public BookingResponse createBooking(final BookingRequest request) {
        // check if user exists
        final Customer customer = customerRepository.findById(request.getUserId()).orElse(null);
        if(customer ==null){
            throw new RuntimeException("User not found");
        }
        // check if there is enough inventory
        final InventoryResponse inventoryResponse = inventoryServiceClient.getInventory(request.getEventId());
        System.out.println("Inventory Service Response"+inventoryResponse);
        // get events info to also get venue info
        // create booking
        // send booking to order service on a kafka topic

        return BookingResponse.builder().build();
    }
}
