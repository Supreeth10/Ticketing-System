package com.example.ticketSystem.bookingservice.service;

import com.example.ticketSystem.bookingservice.repository.CustomerRepository;
import com.example.ticketSystem.bookingservice.request.BookingRequest;
import com.example.ticketSystem.bookingservice.response.BookingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ticketSystem.bookingservice.entity.Customer;

@Service
public class BookingService {

    private final CustomerRepository customerRepository;

    @Autowired
    public BookingService(final CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public BookingResponse createBooking(final BookingRequest request) {
        // check if user exists
        final Customer customer = customerRepository.findById(request.getUserId()).orElse(null);
        if(customer ==null){
            throw new RuntimeException("User not found");
        }
        // check if there is enough inventory
        // get events info to also get venue info
        // create booking
        // send booking to order service on a kafka topic

        return BookingResponse.builder().build();
    }
}
